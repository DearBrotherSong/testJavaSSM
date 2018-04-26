package test02.Infrastructure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test02.Data.CustomerEntity;
import test02.Data.CustomerRoleEntity;
import test02.Infrastructure.CommonTools.APIReturn;
import test02.Infrastructure.CommonTools.CommonTool;
import test02.Infrastructure.sql.CustomerMapper;
import test02.Infrastructure.sql.CustomerRoleMapper;
import test02.Infrastructure.sql.DepartmentMapper;
import test02.Infrastructure.sql.RoleMapper;

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

    public CustomerService(DepartmentMapper departmentMapper,CustomerRoleMapper customerRoleMapper,CustomerMapper customerMapper,RoleMapper roleMapper){
        this._departmentMapper = departmentMapper;
        this._customerMapper = customerMapper;
        this._customerRoleMapper = customerRoleMapper;
        this._roleMapper = roleMapper;
    }


    /*
    注册
     */
    public ConcurrentHashMap Register(CustomerEntity customer) {
        if(customer == null)
            return new APIReturn().CheckParamFaild();

        //参数初步验证
        if(customer.getDepartment_id() == null ||
                customer.getDepartment_id()<1 ||
                CommonTool.Tools.isNullOrWhiteSpace(customer.getNickName()) ||
                CommonTool.Tools.isNullOrWhiteSpace(customer.getPassword()) ||
                CommonTool.Tools.isNullOrWhiteSpace(customer.getUserName()) ||
                CommonTool.Tools.isNullOrWhiteSpace(customer.getEmail()) ||
                customer.getPassword().length()<6 ||
                customer.getPassword().length()>20 ||
                customer.getEmail().length()>50 ||
                customer.getUserName().length() > 50)
            return new APIReturn().CheckParamFaild();

        //密码格式不正确
        if(!customer.getPassword().matches("[A-Za-z0-9]+$"))
            return new APIReturn().PasswordFormatWrong();

        //邮箱格式验证
        if(!CommonTool.Tools.CheckEmailFormat(customer.getEmail()))
            return new APIReturn().EmailFormatWrong();

        if(customer.getDepartment_id()<1)
            customer.setDepartment_id(1);
        //人员所属部门是否存在(0:不属于任何部门)
        if(_departmentMapper.getDepartmentById(customer.getDepartment_id())==null)
            return  new APIReturn().DeptOrParentWrong();
        //邮箱、用户名、昵称是否已经注册
        if(_customerMapper.getBasicByEmailOrName(customer.getEmail(),customer.getUserName(),customer.getNickName(),(long)0)!=null)
            return new APIReturn().CheckEmailFaild();

        customer.setPassword(CommonTool.Tools.GetMD5(customer.getPassword()));
        //数据入库
        _customerMapper.register(customer);
        return new APIReturn().Success();
    }

    /*
    登录并保存登录状态和权限状态
     */
    public ConcurrentHashMap Login(String username,String password, HttpSession session) {
        if(CommonTool.Tools.isNullOrWhiteSpace(username) || CommonTool.Tools.isNullOrWhiteSpace(password))
            return new APIReturn().CheckParamFaild();

        CustomerEntity loginUser = _customerMapper.getByUserName(username);
        if(loginUser!=null && CommonTool.Tools.GetMD5(password).equals(loginUser.getPassword()))
        {
            int result = _customerMapper.updateLastLoginTime(new Timestamp(System.currentTimeMillis()),loginUser.getId());
            if(result != 1)
                return new APIReturn().TokenWrong();
            loginUser.setPassword("");
            String token = UUID.randomUUID().toString();
            session.setAttribute("currentUser",loginUser);
            session.setAttribute(token,loginUser);
            List<String> roleNameList = _roleMapper.getRoleNamesByCustomerId(loginUser.getId());
            session.setAttribute(token.concat("_roleList"),roleNameList);
            ConcurrentHashMap resultData = CommonTool.Tools.entityToMap(loginUser);
            resultData.put("token",token);
            resultData.put("expires_in",CommonTool.SessionTime.Default.getDefault());
            return new APIReturn().apiReturn(CommonTool.CodeEnum.Success.getCode(),"登录成功",resultData);
        }

        return  new APIReturn().LogField();
    }
    /*
    获取用户信息
     */
    public ConcurrentHashMap CustomerInfo(Long id,boolean isCurrent,HttpSession session) {
        if(isCurrent)
        {
            if(session.getAttribute("currentUser") == null)
                return new APIReturn().LoginLost();
            return new APIReturn().apiReturn(CommonTool.CodeEnum.Success.getCode(),"",session.getAttribute("currentUser"));
        }
        if(id == null || id < 0)
            return new APIReturn().CheckParamFaild();

        HashMap customer = _customerMapper.getUserMessDetailById(id);
        if(customer==null)
            return new APIReturn().GetCustomerField();
        return new APIReturn().apiReturn(CommonTool.CodeEnum.Success.getCode(),"",customer);
    }
    /*
    获取用户列表
     */
    public ConcurrentHashMap CustomerList(String userName,Long department_id,Integer page,Integer size) {
        int pageInt = page == null || page.intValue() < 1 ? 1 : page.intValue();
        int sizeInt = page == null || size.intValue() <1 ? 5 : size.intValue();

        userName = CommonTool.Tools.isNullOrWhiteSpace(userName) ? "" : userName;
        String idPath = department_id==null ||department_id <= 1 ? "" : CommonTool.Tools.stringConcat("/",department_id.toString(),"/");

        List<HashMap> customerList = _customerMapper.list(userName,idPath,(pageInt-1)*sizeInt,sizeInt);
        int total=_customerMapper.getListTotal(userName,idPath,(pageInt-1)*sizeInt,sizeInt);
        ConcurrentHashMap result = new ConcurrentHashMap();
        result.put("total",total);
        result.put("data",customerList);
        return result;
    }
    /*
    修改用户基本信息（昵称、部门、邮箱）
     */
    public ConcurrentHashMap UpdateCustomer(Long CustomerId,String nickName,String email,Long department_id,boolean isCurrent,HttpSession session){
        //邮箱格式验证
        if(!CommonTool.Tools.CheckEmailFormat(email))
            return new APIReturn().EmailFormatWrong();

        CustomerEntity updateUser = new CustomerEntity();
        Long departmentId = (long)0;
        if(isCurrent) {
            if(session.getAttribute("currentUser") == null)
                return new APIReturn().LoginLost();
            updateUser = (CustomerEntity)session.getAttribute("currentUser");
            departmentId = updateUser.getDepartment_id();
            CustomerId = updateUser.getId();
        }
        else {
            updateUser = _customerMapper.getById(CustomerId);
            department_id = department_id <1?1:department_id;
            departmentId = department_id;
            //人员所属部门是否存在(0:不属于任何部门)
            if(_departmentMapper.getDepartmentById(department_id)==null)
                return  new APIReturn().DeptOrParentWrong();
        }

        if(updateUser == null)
            return new APIReturn().GetCustomerField();

        //邮箱、用户名、昵称是否已经注册
        if(_customerMapper.getBasicByEmailOrName(updateUser.getUserName(),email,nickName,CustomerId)!=null)
            return new APIReturn().CheckEmailFaild();

        int result = _customerMapper.updateCustomerBasic(departmentId,email,nickName,CustomerId);
        if(result != 1)
            return new APIReturn().TargetLost();
        return new APIReturn().Success();
    }
    /*
    修改密码
     */
    public ConcurrentHashMap ResetPassword(Long id,String newPassword,String confirmPassword){
        if(newPassword == null || newPassword.length()<6||newPassword.length()>20||!newPassword.equals(confirmPassword))
            return new APIReturn().PasswordResetCheckFaild();

        CustomerEntity currentUser = _customerMapper.getById(id);
        if(currentUser == null)
            return new APIReturn().LoginLost();

        int result = _customerMapper.updatePassword(currentUser.getId(), CommonTool.Tools.GetMD5(newPassword));
        if(result != 1)
            return  new APIReturn().PasswordResetFaild();

        return new APIReturn().Success();
    }
    /*
    删除人员
     */
    @Transactional
    public ConcurrentHashMap DeleteCustomer(Long id){
        if(id==null)
            return new APIReturn().CheckParamFaild();

        if(_customerMapper.getById(id) == null)
            return new APIReturn().GetCustomerField();

        _customerRoleMapper.deleteByCustomerId(id);
        _customerMapper.deleteCustomer(id);

        return new APIReturn().Success();
    }
    /*
    给人员赋予角色属性
     */
    public ConcurrentHashMap AddCustomerRole(Long customerId,Long roleId){
        if(customerId == null || roleId == null)
            return new APIReturn().CheckParamFaild();
        if(customerId<0||_customerMapper.getById(customerId)==null)
            return new APIReturn().GetCustomerField();
        if(roleId<0||_roleMapper.getById(roleId) == null)
            return new APIReturn().RoleIdCheckFail();

        CustomerRoleEntity customerRoleEntity = new CustomerRoleEntity();
        customerRoleEntity.setCustomerId(customerId);
        customerRoleEntity.setRoleId(roleId);

        if(_customerRoleMapper.getByCustomerIdAndRoleId(customerRoleEntity) != null)
            return new APIReturn().CustomerOwnedRole();

        try {
            _customerRoleMapper.insert(customerRoleEntity);
            return new APIReturn().Success();
        }catch (Exception e){
            return new APIReturn().UnknownExceptionProduce();
        }
    }
}
