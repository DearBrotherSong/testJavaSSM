package test02.Domain.department;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

import test02.Infrastructure.service.DepartmentService;

public class DepartmentDomain {
    private ApplicationContext applicationContext;

    @Resource
    private DepartmentService departmentService;


    public DepartmentService getDepartmentService(){
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");//加载spring配置文件
        departmentService = applicationContext.getBean(DepartmentService.class);
        return departmentService;
    }

    /*
    部门初始化（添加顶层部门）
     */
    public ConcurrentHashMap InitDepartment(){
        return getDepartmentService().InitDepartment();
    }

    /*
    查询所有部门（isTree：是否返回部门树）
     */
    public ConcurrentHashMap AllDepartment(boolean isTree){
        return getDepartmentService().FindAll(isTree);
    }

    /*
    添加部门
     */
    public ConcurrentHashMap AddDepartment(String name,Long parentId,String managerEmail,String description){
        return getDepartmentService().AddDepartment(name,parentId,managerEmail,description);
    }

    /*
    修改部门
     */
    public ConcurrentHashMap UpdateDepartment(Long id,String name,String managerEmail,String description){
        return getDepartmentService().UpdateDepartment(id,name,managerEmail,description);
    }

    /*
    删除部门
     */
    public ConcurrentHashMap DeleteDepartment(Long id){
        return getDepartmentService().DeleteDepartment(id);
    }
}
