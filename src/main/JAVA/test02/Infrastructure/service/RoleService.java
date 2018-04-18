package test02.Infrastructure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.ParseState;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test02.Domain.RoleEntity;
import test02.Infrastructure.CommonTools.APIReturn;
import test02.Infrastructure.CommonTools.CommonTool;
import test02.Infrastructure.sql.CustomerMapper;
import test02.Infrastructure.sql.CustomerRoleMapper;
import test02.Infrastructure.sql.DepartmentMapper;
import test02.Infrastructure.sql.RoleMapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoleService {
    private ApplicationContext applicationContext;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private CustomerRoleMapper customerRoleMapper;

    public CustomerRoleMapper getCustomerRoleMapperDao(){
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");//加载spring配置文件
        customerRoleMapper = applicationContext.getBean(CustomerRoleMapper.class);
        return customerRoleMapper;
    }
    public RoleMapper getRoleMapperDao(){
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");//加载spring配置文件
        roleMapper = applicationContext.getBean(RoleMapper.class);
        return roleMapper;
    }
    /*
    添加角色
     */
    public ConcurrentHashMap AddRole(String name,String description){
        if(CommonTool.Tools.isNullOrWhiteSpace(name) || name.length() > 50 ||
                (!CommonTool.Tools.isNullOrWhiteSpace(name) && description.length() >200))
            return new APIReturn().CheckParamFaild();

        roleMapper = getRoleMapperDao();
        if(roleMapper.getByName(name,(long)0) != null)
            return new APIReturn().RoleNameCheckFail();

        RoleEntity role = new RoleEntity();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        role.setName(name);
        role.setCreateTime(now);
        role.setDescription(description);
        role.setState(CommonTool.State.StateOn.getState());

        try {
            roleMapper.addRole(role);
        }catch (Exception e){
            return new APIReturn().UnknownExceptionProduce();
        }
        return new APIReturn().Success();
    }

    /*
    修改角色
     */
    public ConcurrentHashMap UpdateRole(Long id,String name,String description)
    {
        if(CommonTool.Tools.isNullOrWhiteSpace(name) || name.length() > 50 ||
                (!CommonTool.Tools.isNullOrWhiteSpace(name) && description.length() >200))
            return new APIReturn().CheckParamFaild();

        roleMapper = getRoleMapperDao();
        RoleEntity currentRole = roleMapper.getById(id);
        if(currentRole == null)
            return new APIReturn().RoleIdCheckFail();
        if(roleMapper.getByName(name,id) != null)
            return new APIReturn().RoleNameCheckFail();

        try {
            currentRole.setName(name);
            currentRole.setDescription(description);
            roleMapper.updateById(currentRole);
        }catch (Exception e){
            return new APIReturn().UnknownExceptionProduce();
        }
        return new APIReturn().Success();

    }

    /*
    删除角色
     */
    @Transactional
    public ConcurrentHashMap DeleteRole(Long id)
    {
        roleMapper = getRoleMapperDao();
        customerRoleMapper = getCustomerRoleMapperDao();
        RoleEntity currentRole = roleMapper.getById(id);
        if(currentRole == null)
            return new APIReturn().RoleIdCheckFail();

        try {
            customerRoleMapper.deleteByRoleId(id);
            roleMapper.deleteById(id);
        }catch (Exception e){
            return new APIReturn().UnknownExceptionProduce();
        }
        return new APIReturn().Success();
    }

    /*
    角色列表
     */
    public ConcurrentHashMap RoleList()
    {
        roleMapper = getRoleMapperDao();

        List<RoleEntity> roleList = roleMapper.findAll();
        return new APIReturn().apiReturn(CommonTool.CodeEnum.Success.getCode(),"",roleList);
    }
    /*
    角色详情
     */
    public ConcurrentHashMap RoleInfo(Long id)
    {
        roleMapper = getRoleMapperDao();
        RoleEntity roleInfo = roleMapper.getById(id);
        if(roleInfo == null)
            return new APIReturn().RoleIdCheckFail();
        return new APIReturn().apiReturn(CommonTool.CodeEnum.Success.getCode(),"",roleInfo);

    }
}
