package test02.Application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.concurrent.ConcurrentHashMap;

import test02.Domain.department.DepartmentDomain;

@Controller
@RequestMapping("/private/departments")
public class DepartmentController {
    @RequestMapping("/init")
    @ResponseBody
    public ConcurrentHashMap initDepartment() {
        DepartmentDomain deptDomain = new DepartmentDomain();
        return  deptDomain.InitDepartment();
    }
    /*
    获取部门列表（或树结构）
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ConcurrentHashMap allDepartment(boolean isTree) {
        DepartmentDomain deptDomain = new DepartmentDomain();
        return  deptDomain.AllDepartment(isTree);
    }
    /*
    添加部门
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ConcurrentHashMap addDepartment(String name,Long parentId,String managerEmail,String description) {
        DepartmentDomain deptDomain = new DepartmentDomain();
        return  deptDomain.AddDepartment(name,parentId,managerEmail,description);
    }
    /*
    修改部门
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ConcurrentHashMap updateDepartment(@PathVariable("id") Long id, String name, String managerEmail, String description) {
        DepartmentDomain deptDomain = new DepartmentDomain();
        return  deptDomain.UpdateDepartment(id,name,managerEmail,description);
    }
    /*
    删除部门
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ConcurrentHashMap deleteDepartment(@PathVariable("id") Long id) {
        DepartmentDomain deptDomain = new DepartmentDomain();
        return  deptDomain.DeleteDepartment(id);
    }
}
