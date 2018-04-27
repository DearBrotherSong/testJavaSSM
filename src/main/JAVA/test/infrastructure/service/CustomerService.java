package test.infrastructure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.data.CustomerEntity;
import test.data.CustomerRoleEntity;
import test.infrastructure.common.ApiReturn;
import test.infrastructure.common.CommonTool;
import test.infrastructure.sql.CustomerMapper;
import test.infrastructure.sql.CustomerRoleMapper;
import test.infrastructure.sql.DepartmentMapper;
import test.infrastructure.sql.RoleMapper;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CustomerService {
    @Autowired
    private DepartmentMapper _departmentMapper;
    @Autowired
    private CustomerMapper _customerMapper;
    @Autowired
    private RoleMapper _roleMapper;
    @Autowired
    private CustomerRoleMapper _customerRoleMapper;

    public CustomerService(DepartmentMapper departmentMapper,CustomerRoleMapper customerRoleMapper,CustomerMapper customerMapper,RoleMapper roleMapper) {
        this._departmentMapper = departmentMapper;
        this._customerMapper = customerMapper;
        this._customerRoleMapper = customerRoleMapper;
        this._roleMapper = roleMapper;
    }

    /*
    注册
     */
    public ConcurrentHashMap register(CustomerEntity customer) {
        if (customer == null) {
            return new ApiReturn().checkParamFaild();
        }

        //参数初步验证
        if (customer.getDepartmentId() == null
                || customer.getDepartmentId() < 1
                || CommonTool.Tools.isNullOrWhiteSpace(customer.getNickName())
                || CommonTool.Tools.isNullOrWhiteSpace(customer.getPassword())
                || CommonTool.Tools.isNullOrWhiteSpace(customer.getUserName())
                ||  CommonTool.Tools.isNullOrWhiteSpace(customer.getEmail())
                || customer.getPassword().length() < 6
                || customer.getPassword().length() > 20
                || customer.getEmail().length() > 50
                || customer.getUserName().length() > 50) {
            return new ApiReturn().checkParamFaild();
        }


        //密码格式不正确
        if (!customer.getPassword().matches("[A-Za-z0-9]+$")) {
            return new ApiReturn().passwordFormatWrong();
        }


        //邮箱格式验证
        if (!CommonTool.Tools.checkEmailFormat(customer.getEmail())) {
            return new ApiReturn().emailFormatWrong();
        }


        if (customer.getDepartmentId() < 1) {
            customer.setDepartmentId(1);
        }

        //人员所属部门是否存在(0:不属于任何部门)
        if (_departmentMapper.getDepartmentById(customer.getDepartmentId()) == null) {
            return  new ApiReturn().deptOrParentWrong();
        }

        //邮箱、用户名、昵称是否已经注册
        if (_customerMapper.getBasicByEmailOrName(customer.getEmail(),customer.getUserName(),customer.getNickName(),(long)0) != null) {
            return new ApiReturn().checkEmailFaild();
        }

        customer.setPassword(CommonTool.Tools.GetMD5(customer.getPassword()));
        //数据入库
        _customerMapper.register(customer);
        return new ApiReturn().success();
    }

    /*
    登录并保存登录状态和权限状态
     */
    public ConcurrentHashMap login(String username,String password, HttpSession session) {
        if (CommonTool.Tools.isNullOrWhiteSpace(username) || CommonTool.Tools.isNullOrWhiteSpace(password)) {
            return new ApiReturn().checkParamFaild();
        }

        CustomerEntity loginUser = _customerMapper.getByUserName(username);
        if (loginUser != null && CommonTool.Tools.GetMD5(password).equals(loginUser.getPassword())) {
            int result = _customerMapper.updateLastLoginTime(new Timestamp(System.currentTimeMillis()),loginUser.getId());
            if (result != 1) {
                return new ApiReturn().tokenWrong();
            }
            loginUser.setPassword("");
            String token = UUID.randomUUID().toString();
            session.setAttribute("currentUserId",loginUser.getId());
            session.setAttribute(token,loginUser);
            List<String> roleNameList = _roleMapper.getRoleNamesByCustomerId(loginUser.getId());
            session.setAttribute(token.concat("_roleList"),roleNameList);
            ConcurrentHashMap resultData = CommonTool.Tools.entityToMap(loginUser);
            resultData.put("token",token);
            resultData.put("expires_in",CommonTool.SessionTime.Default.getDefault());
            return new ApiReturn().apiReturn(CommonTool.CodeEnum.Success.getCode(),"登录成功",resultData);
        }

        return  new ApiReturn().logField();
    }

    /*
    获取用户信息
     */
    public ConcurrentHashMap customerInfo(Long id,boolean isCurrent,HttpSession session) {
        if (isCurrent) {
            if (session.getAttribute("currentUserId") == null) {
                return new ApiReturn().loginLost();
            }
            CustomerEntity currentUser = _customerMapper.getById(Long.parseLong(session.getAttribute("currentUserId").toString()));
            if (currentUser == null) {
                session.invalidate();
                return  new ApiReturn().loginLost();
            }
            return new ApiReturn().apiReturn(CommonTool.CodeEnum.Success.getCode(),"",currentUser);
        }
        if (id == null || id < 0) {
            return new ApiReturn().checkParamFaild();
        }


        HashMap customer = _customerMapper.getUserMessDetailById(id);
        if (customer == null) {
            return new ApiReturn().getCustomerField();
        }

        return new ApiReturn().apiReturn(CommonTool.CodeEnum.Success.getCode(),"",customer);
    }

    /*
    获取用户列表
     */
    public ConcurrentHashMap customerList(String userName,Long departmentId,Integer page,Integer size) {
        int pageInt = page == null || page.intValue() < 1 ? 1 : page.intValue();
        int sizeInt = page == null || size.intValue() < 1 ? 5 : size.intValue();

        userName = CommonTool.Tools.isNullOrWhiteSpace(userName) ? "" : userName;
        String idPath = departmentId == null || departmentId <= 1 ? "" : CommonTool.Tools.stringConcat("/",departmentId.toString(),"/");

        List<HashMap> customerList = _customerMapper.list(userName,idPath,(pageInt - 1) * sizeInt,sizeInt);
        int total = _customerMapper.getListTotal(userName,idPath,(pageInt - 1) * sizeInt,sizeInt);
        ConcurrentHashMap result = new ConcurrentHashMap();
        result.put("total",total);
        result.put("data",customerList);
        return result;
    }

    /*
    修改用户基本信息（昵称、部门、邮箱）
     */
    public ConcurrentHashMap updateCustomer(Long customerId,String nickName,String email,Long departmentId,boolean isCurrent,HttpSession session) {
        //邮箱格式验证
        if (!CommonTool.Tools.checkEmailFormat(email)) {
            return new ApiReturn().emailFormatWrong();
        }

        CustomerEntity updateUser;
        Long deptId;
        if (isCurrent) {
            if (session.getAttribute("currentUserId") == null) {
                return new ApiReturn().loginLost();
            }

            updateUser = _customerMapper.getById(Long.parseLong(session.getAttribute("currentUserId").toString()));
            if (updateUser == null) {
                session.invalidate();
                return new ApiReturn().loginLost();
            }
            deptId = updateUser.getDepartmentId();
            customerId = updateUser.getId();
        } else {
            updateUser = _customerMapper.getById(customerId);
            departmentId = departmentId < 1 ? 1 : departmentId;
            deptId = departmentId;

            //人员所属部门是否存在(0:不属于任何部门)
            if (_departmentMapper.getDepartmentById(deptId) == null) {
                return  new ApiReturn().deptOrParentWrong();
            }

        }

        if (updateUser == null) {
            return new ApiReturn().getCustomerField();
        }

        //邮箱、用户名、昵称是否已经注册
        if (_customerMapper.getBasicByEmailOrName(updateUser.getUserName(),email,nickName,customerId) != null) {
            return new ApiReturn().checkEmailFaild();
        }

        int result = _customerMapper.updateCustomerBasic(deptId,email,nickName,customerId);
        if (result != 1) {
            return new ApiReturn().targetLost();
        }

        return new ApiReturn().success();
    }

    /*
    修改密码
     */
    public ConcurrentHashMap resetPassword(Long id,String newPassword,String confirmPassword) {
        if (newPassword == null || newPassword.length() < 6 || newPassword.length() > 20 || !newPassword.equals(confirmPassword)) {
            return new ApiReturn().passwordResetCheckFaild();
        }

        CustomerEntity currentUser = _customerMapper.getById(id);
        if (currentUser == null) {
            return new ApiReturn().loginLost();
        }

        int result = _customerMapper.updatePassword(currentUser.getId(), CommonTool.Tools.GetMD5(newPassword));
        if (result != 1) {
            return  new ApiReturn().passwordResetFaild();
        }

        return new ApiReturn().success();
    }

    /*
    删除人员
     */
    @Transactional
    public ConcurrentHashMap deleteCustomer(Long id) {
        if (id == null) {
            return new ApiReturn().checkParamFaild();
        }

        if (_customerMapper.getById(id) == null) {
            return new ApiReturn().getCustomerField();
        }

        _customerRoleMapper.deleteByCustomerId(id);
        _customerMapper.deleteCustomer(id);

        return new ApiReturn().success();
    }

    /*
    给人员赋予角色属性
     */
    public ConcurrentHashMap addCustomerRole(Long customerId,Long roleId) {
        if (customerId == null || roleId == null) {
            return new ApiReturn().checkParamFaild();
        }

        if (customerId < 0 || _customerMapper.getById(customerId) == null) {
            return new ApiReturn().getCustomerField();
        }

        if (roleId < 0 || _roleMapper.getById(roleId) == null) {
            return new ApiReturn().roleIdCheckFail();
        }

        CustomerRoleEntity customerRoleEntity = new CustomerRoleEntity();
        customerRoleEntity.setCustomerId(customerId);
        customerRoleEntity.setRoleId(roleId);

        if (_customerRoleMapper.getByCustomerIdAndRoleId(customerRoleEntity) != null) {
            return new ApiReturn().customerOwnedRole();
        }

        try {
            _customerRoleMapper.insert(customerRoleEntity);
            return new ApiReturn().success();
        } catch (Exception e) {
            return new ApiReturn().unknownExceptionProduce();
        }
    }
}
