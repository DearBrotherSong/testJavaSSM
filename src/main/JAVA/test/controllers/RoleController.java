package test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import test.infrastructure.service.RoleService;

import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/private/roles")
public class RoleController {
    private RoleService _roleService;

    @Autowired
    public RoleController(RoleService roleService){
        this._roleService = roleService;
    }

    /*
    添加角色
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ConcurrentHashMap AddRole(String name, String description){
        return  _roleService.AddRole(name,description);
    }
    /*
    修改角色
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ConcurrentHashMap UpdateRole(@PathVariable("id") Long id,String name,String description){
        return  _roleService.UpdateRole(id,name,description);
    }
    /*
    删除角色
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public ConcurrentHashMap DeleteRole(@PathVariable("id") Long id){
        return  _roleService.DeleteRole(id);
    }
    /*
    角色列表
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public ConcurrentHashMap RoleList(){
        return  _roleService.RoleList();
    }

}
