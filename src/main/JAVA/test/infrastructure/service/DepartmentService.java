package test.infrastructure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import test.data.CustomerEntity;
import test.data.DepartmentEntity;
import test.infrastructure.common.ApiReturn;
import test.infrastructure.common.CommonTool;
import test.infrastructure.sql.CustomerMapper;
import test.infrastructure.sql.DepartmentMapper;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper _departmentMapper;
    @Autowired
    private CustomerMapper _customerMapper;

    public DepartmentService(DepartmentMapper departmentMapper,CustomerMapper customerMapper) {
        this._customerMapper = customerMapper;
        this._departmentMapper = departmentMapper;
    }

    /*
    初始化部门根节点
     */
    public ConcurrentHashMap initDepartment() {
        //根节点已存在
        DepartmentEntity parentDept = _departmentMapper.getByParentId((long)0);
        if (parentDept != null) {
            return  new ApiReturn().initDeptOrParentWrong();
        }

        DepartmentEntity dept = new DepartmentEntity();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        dept.setName("东方");
        dept.setParentId((long) 0);
        dept.setCreateTime(now);
        dept.setDescription("公司");
        dept.setManager("");
        dept.setState(CommonTool.State.StateOn.getState());

        return addAction(dept,null);
    }

    /*
    获取部门列表（list或tree）
     */
    public ConcurrentHashMap findAll(boolean isTree) {
        List<DepartmentEntity> allDept = _departmentMapper.findAllAsList();
        Object result = allDept;
        if (isTree) {
            result = getDeptTree(allDept);
        }

        return new ApiReturn().apiReturn(CommonTool.CodeEnum.Success.getCode(),"",result);
    }

    /*
    添加部门
     */
    public ConcurrentHashMap addDepartment(String name,Long parentId,String managerEmail,String description) {
        //参数验证
        if (CommonTool.Tools.isNullOrWhiteSpace(name)) {
            return new ApiReturn().checkParamFaild();
        }
        if (name.length() > 30 || parentId <= 0 || (!CommonTool.Tools.isNullOrWhiteSpace(description) && description.length() > 200)) {
            return new ApiReturn().checkParamFaild();
        }

        //检测上级部门是否存在
        DepartmentEntity parentDept = _departmentMapper.getDepartmentById(parentId);
        if (parentDept == null) {
            return  new ApiReturn().deptOrParentWrong();
        }

        //检测主管邮箱是否有效
        if (!CommonTool.Tools.isNullOrWhiteSpace(managerEmail) && _customerMapper.getBasicByEmail(managerEmail) == null) {
            return new ApiReturn().checkManagerEmailFaild();
        }

        //入库动作
        DepartmentEntity dept = new DepartmentEntity();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        dept.setName(name);
        dept.setParentId(parentId);
        dept.setCreateTime(now);
        dept.setDescription(description);
        dept.setManager(managerEmail);
        dept.setState(CommonTool.State.StateOn.getState());

        return addAction(dept,parentDept);
    }

    /*
    修改部门（名称，说明）
     */
    @Transactional
    public ConcurrentHashMap updateDepartment(Long id,String name,String managerEmail,String description)
    {
        //检测参数
        if (id <= 0 || CommonTool.Tools.isNullOrWhiteSpace(name)) {
            return new ApiReturn().checkParamFaild();
        }
        if (name.length() > 30 || (!CommonTool.Tools.isNullOrWhiteSpace(description) && description.length() > 200)) {
            return new ApiReturn().checkParamFaild();
        }
        //检测主管邮箱是否有效
        if (!CommonTool.Tools.isNullOrWhiteSpace(managerEmail) && _customerMapper.getBasicByEmail(managerEmail) == null) {
            return new ApiReturn().checkManagerEmailFaild();
        }

        //部门是否存在
        DepartmentEntity dept = _departmentMapper.getDepartmentById(id);
        if (dept == null) {
            return  new ApiReturn().deptOrParentWrong();
        }
        managerEmail = CommonTool.Tools.isNullOrWhiteSpace(managerEmail) ? dept.getManager() : managerEmail;

        //修改部门名称和子部门名称路径
        List<DepartmentEntity> allChilds = _departmentMapper.getDepartmentByIdPath(CommonTool.Tools.stringConcat("/",dept.getId().toString(),"/"));
        _departmentMapper.updateDepartment(id,name,managerEmail,description);

        if (!dept.getName().equals(name)) {
            List<Long> childIds = new ArrayList<Long>();
            for (DepartmentEntity child:allChilds) {
                childIds.add(child.getId());
            }
            String oldName = CommonTool.Tools.stringConcat("/",dept.getName(),"/");
            String newName = CommonTool.Tools.stringConcat("/",name,"/");
            _departmentMapper.updateChildNamePath(oldName,newName,childIds);
        }

        return new ApiReturn().success();
    }

    /*
    删除部门
     */
    @Transactional
    public ConcurrentHashMap deleteDepartment(Long id) {
        //检测参数
        if (id <= 0) {
            return new ApiReturn().checkParamFaild();
        }

        //部门是否存在
        List<DepartmentEntity> deptList = _departmentMapper.getDepartmentByIdPath(CommonTool.Tools.stringConcat("/",id.toString(),"/"));
        if (deptList.size() <= 0) {
            return  new ApiReturn().deptOrParentWrong();
        }

        List<Long> ids = new ArrayList<Long>();
        for (DepartmentEntity dept : deptList) {
            ids.add(dept.getId());
        }

        //部门和下级部门是否有成员
        List<CustomerEntity> deptCustomerList = _customerMapper.getByDepartmentIds(ids);
        if (deptCustomerList.size() > 0) {
            return  new ApiReturn().departmentHasCustomer();
        }

        //删除部门和下级部门
        _departmentMapper.deleteDepartment(ids);
        return new ApiReturn().success();
    }

    /*
    添加部门事务
     */
    @Transactional
    public ConcurrentHashMap addAction(DepartmentEntity dept, DepartmentEntity parentDept) {
        _departmentMapper.addDepartment(dept);
        String pathCode = "/";
        String idPath = CommonTool.Tools.stringConcat(parentDept == null ? pathCode : parentDept.getIdPath(),dept.getId().toString(),pathCode);
        String namePath = CommonTool.Tools.stringConcat(parentDept == null ? pathCode : parentDept.getnamePath(),dept.getName(),pathCode);

        dept.setIdPath(idPath);
        dept.setnamePath(namePath);
        _departmentMapper.updateDepartmentPath(dept);
        return new ApiReturn().success();
    }

    /*
    部门树结构工具
     */
    public ConcurrentHashMap getDeptTree(List<DepartmentEntity> allDept) {
        ConcurrentHashMap tree = new ConcurrentHashMap();
        for (DepartmentEntity dept : allDept) {
            if (dept.getParentId() == 0) {
                tree = CommonTool.Tools.entityToMap(dept);
                allDept.remove(dept);
                break;
            }
        }
        tree = getChilds(tree,allDept);
        return tree;
    }

    public ConcurrentHashMap getChilds(ConcurrentHashMap top,List<DepartmentEntity> allDept) {
        List<ConcurrentHashMap> childs = new ArrayList<ConcurrentHashMap>();
        for (DepartmentEntity dept : allDept) {
            if (dept.getParentId() == top.get("id")) {
                ConcurrentHashMap item = CommonTool.Tools.entityToMap(dept);
                item = getChilds(item,allDept);
                childs.add(item);
            }
        }
        if (childs.size() > 0) {
            top.put("childs",childs);
        }
        return top;
    }

}
