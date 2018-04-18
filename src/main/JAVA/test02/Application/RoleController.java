package test02.Application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import test02.Domain.role.RoleDomain;

import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/private/roles")
public class RoleController {

    /*
    添加角色
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ConcurrentHashMap AddRole(String name, String description){
        RoleDomain roleDomain = new RoleDomain();
        return  roleDomain.AddRole(name,description);
    }
    /*
    修改角色
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ConcurrentHashMap UpdateRole(@PathVariable("id") Long id,String name,String description){
        RoleDomain roleDomain = new RoleDomain();
        return  roleDomain.UpdateRole(id,name,description);
    }
    /*
    删除角色
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public ConcurrentHashMap DeleteRole(@PathVariable("id") Long id){
        RoleDomain roleDomain = new RoleDomain();
        return  roleDomain.DeleteRole(id);
    }
    /*
    角色列表
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public ConcurrentHashMap RoleList(){
        RoleDomain roleDomain = new RoleDomain();
        return  roleDomain.RoleList();
    }

}
