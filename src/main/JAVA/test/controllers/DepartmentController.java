package test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.concurrent.ConcurrentHashMap;

import test.infrastructure.service.DepartmentService;

@Controller
@RequestMapping("/private/departments")
public class DepartmentController {
    private DepartmentService _departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this._departmentService = departmentService;
    }

    @RequestMapping("/init")
    @ResponseBody
    public ConcurrentHashMap initDepartment() {
        return  _departmentService.initDepartment();
    }

    /*
    获取部门列表（或树结构）
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ConcurrentHashMap allDepartment(boolean isTree) {
        return  _departmentService.findAll(isTree);
    }

    /*
    添加部门
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ConcurrentHashMap addDepartment(String name,Long parentId,String managerEmail,String description) {
        return  _departmentService.addDepartment(name,parentId,managerEmail,description);
    }

    /*
    修改部门
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ConcurrentHashMap updateDepartment(@PathVariable("id") Long id, String name, String managerEmail, String description) {
        return  _departmentService.updateDepartment(id,name,managerEmail,description);
    }

    /*
    删除部门
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ConcurrentHashMap deleteDepartment(@PathVariable("id") Long id) {
        return  _departmentService.deleteDepartment(id);
    }
}
