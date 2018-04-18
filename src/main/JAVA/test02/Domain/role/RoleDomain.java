package test02.Domain.role;

import javax.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import test02.Infrastructure.service.RoleService;

import java.util.concurrent.ConcurrentHashMap;

public class RoleDomain {
    private ApplicationContext applicationContext;

    @Resource
    private RoleService roleService;


    public RoleService getDepartmentService(){
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");//加载spring配置文件
        roleService = applicationContext.getBean(RoleService.class);
        return roleService;
    }

    public ConcurrentHashMap AddRole(String name,String description){
        return getDepartmentService().AddRole(name,description);
    }
    public ConcurrentHashMap UpdateRole(Long id,String name,String description){
        return getDepartmentService().UpdateRole(id,name,description);
    }
    public ConcurrentHashMap DeleteRole(Long id){
        return getDepartmentService().DeleteRole(id);
    }
    public ConcurrentHashMap RoleList(){
        return getDepartmentService().RoleList();
    }
    public ConcurrentHashMap RoleInfo(Long id){
        return getDepartmentService().RoleInfo(id);
    }
}
