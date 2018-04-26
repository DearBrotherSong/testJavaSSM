package test02.Infrastructure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import test02.Data.CustomerEntity;
import test02.Data.DepartmentEntity;
import test02.Infrastructure.CommonTools.APIReturn;
import test02.Infrastructure.CommonTools.CommonTool;
import test02.Infrastructure.sql.CustomerMapper;
import test02.Infrastructure.sql.DepartmentMapper;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper _departmentMapper;
    @Autowired
    private CustomerMapper _customerMapper;

    public DepartmentService(DepartmentMapper departmentMapper,CustomerMapper customerMapper){
        this._customerMapper = customerMapper;
        this._departmentMapper = departmentMapper;
    }
    /*
    初始化部门根节点
     */
    public ConcurrentHashMap InitDepartment()
    {
        //根节点已存在
        DepartmentEntity parentDept = _departmentMapper.getByParentId((long)0);
        if(parentDept != null)
            return  new APIReturn().InitDeptOrParentWrong();

        DepartmentEntity dept = new DepartmentEntity();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        dept.setName("东方");
        dept.setParentId((long) 0);
        dept.setCreateTime(now);
        dept.setDescription("公司");
        dept.setManager("");
        dept.setState(CommonTool.State.StateOn.getState());

        return AddAction(dept,null);
    }

    /*
    获取部门列表（list或tree）
     */
    public ConcurrentHashMap FindAll(boolean isTree){
        List<DepartmentEntity> allDept = _departmentMapper.findAllAsList();
        Object result = allDept;
        if(isTree)
            result = GetDeptTree(allDept);

        return new APIReturn().apiReturn(CommonTool.CodeEnum.Success.getCode(),"",result);
    }

    /*
    添加部门
     */
    public ConcurrentHashMap AddDepartment(String name,Long parentId,String managerEmail,String description){
        //参数验证
        if(CommonTool.Tools.isNullOrWhiteSpace(name))
            return new APIReturn().CheckParamFaild();
        if(name.length() > 30 || parentId <= 0 || (!CommonTool.Tools.isNullOrWhiteSpace(description) && description.length() > 200))
            return new APIReturn().CheckParamFaild();

        //检测上级部门是否存在
        DepartmentEntity parentDept = _departmentMapper.getDepartmentById(parentId);
        if(parentDept == null)
            return  new APIReturn().DeptOrParentWrong();

        //检测主管邮箱是否有效
        if(!CommonTool.Tools.isNullOrWhiteSpace(managerEmail) && _customerMapper.getBasicByEmail(managerEmail) == null)
            return new APIReturn().CheckManagerEmailFaild();

        //入库动作
        DepartmentEntity dept = new DepartmentEntity();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        dept.setName(name);
        dept.setParentId(parentId);
        dept.setCreateTime(now);
        dept.setDescription(description);
        dept.setManager(managerEmail);
        dept.setState(CommonTool.State.StateOn.getState());

        return AddAction(dept,parentDept);
    }

    /*
    修改部门（名称，说明）
     */
    @Transactional
    public ConcurrentHashMap UpdateDepartment(Long id,String name,String managerEmail,String description)
    {
        //检测参数
        if(id<=0 || CommonTool.Tools.isNullOrWhiteSpace(name))
            return new APIReturn().CheckParamFaild();
        if(name.length() > 30 || (!CommonTool.Tools.isNullOrWhiteSpace(description) && description.length() > 200))
            return new APIReturn().CheckParamFaild();
        //检测主管邮箱是否有效
        if(!CommonTool.Tools.isNullOrWhiteSpace(managerEmail) && _customerMapper.getBasicByEmail(managerEmail) == null)
            return new APIReturn().CheckManagerEmailFaild();

        //部门是否存在
        DepartmentEntity dept = _departmentMapper.getDepartmentById(id);
        if(dept == null)
            return  new APIReturn().DeptOrParentWrong();
        managerEmail = CommonTool.Tools.isNullOrWhiteSpace(managerEmail)?dept.getManager():managerEmail;

        //修改部门名称和子部门名称路径
        List<DepartmentEntity> allChilds = _departmentMapper.getDepartmentByIdPath(CommonTool.Tools.stringConcat("/",dept.getId().toString(),"/"));
        _departmentMapper.updateDepartment(id,name,managerEmail,description);
        int aaa=99999;
        if(!dept.getName().equals(name))
        {
            List<Long> childIds = new ArrayList<Long>();
            for (DepartmentEntity child:allChilds)
            {
                childIds.add(child.getId());
            }
            String oldName = CommonTool.Tools.stringConcat("/",dept.getName(),"/");
            String newName = CommonTool.Tools.stringConcat("/",name,"/");
            int update = _departmentMapper.updateChildNamePath(oldName,newName,childIds);
            aaa = update;
        }

        return new APIReturn().Success();
    }
    /*
    删除部门
     */
    @Transactional
    public ConcurrentHashMap DeleteDepartment(Long id)
    {
        //检测参数
        if(id<=0)
            return new APIReturn().CheckParamFaild();

        //部门是否存在
        List<DepartmentEntity> deptList = _departmentMapper.getDepartmentByIdPath(CommonTool.Tools.stringConcat("/",id.toString(),"/"));
        if(deptList.size() <= 0)
            return  new APIReturn().DeptOrParentWrong();

        List<Long> ids = new ArrayList<Long>();
        for (DepartmentEntity dept:deptList)
        {
            ids.add(dept.getId());
        }

        //部门和下级部门是否有成员
        List<CustomerEntity> deptCustomerList = _customerMapper.getByDepartmentIds(ids);
        if(deptCustomerList.size() > 0)
            return  new APIReturn().DepartmentHasCustomer();

        //删除部门和下级部门
        _departmentMapper.deleteDepartment(ids);
        return new APIReturn().Success();
    }

    /*
    添加部门事务
     */
    @Transactional
    public ConcurrentHashMap AddAction( DepartmentEntity dept, DepartmentEntity parentDept)
    {
        _departmentMapper.addDepartment(dept);
        String pathCode = "/";
        String idPath= CommonTool.Tools.stringConcat(parentDept == null?pathCode:parentDept.getIdPath(),dept.getId().toString(),pathCode);
        String namePath=CommonTool.Tools.stringConcat(parentDept == null?pathCode:parentDept.getnamePath(),dept.getName(),pathCode);

        dept.setIdPath(idPath);
        dept.setnamePath(namePath);
        _departmentMapper.updateDepartmentPath(dept);
        return new APIReturn().Success();
    }

    /*
    部门树结构工具
     */
    public ConcurrentHashMap GetDeptTree(List<DepartmentEntity> allDept)
    {
        DepartmentEntity entity = new DepartmentEntity();
        ConcurrentHashMap tree = new ConcurrentHashMap();
        for(DepartmentEntity dept : allDept)
        {
            if(dept.getParentId()==0)
            {
                tree = CommonTool.Tools.entityToMap(dept);
                allDept.remove(dept);
                break;
            }
        }
        tree = getChilds(tree,allDept);
        return tree;
    }
    public ConcurrentHashMap getChilds(ConcurrentHashMap top,List<DepartmentEntity> allDept)
    {
        List<ConcurrentHashMap> childs = new ArrayList<ConcurrentHashMap>();
        for(DepartmentEntity dept : allDept)
        {
            if(dept.getParentId() == top.get("id"))
            {
                ConcurrentHashMap item = CommonTool.Tools.entityToMap(dept);
                item=getChilds(item,allDept);
                childs.add(item);
            }
        }
        if(childs.size() > 0)
            top.put("childs",childs);
        return top;
    }

}
