package test02.Controllers;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import test02.Data.CustomerEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import test02.Infrastructure.service.CustomerService;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/private/users")
public class CustomerController{

    private CustomerService _customerService = null;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this._customerService = customerService;
    }
    /*
    注册接口（添加用户）
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ConcurrentHashMap Regist(String customerJson){
        CustomerEntity customer = JSON.parseObject(customerJson,CustomerEntity.class);
        return _customerService.Register(customer);
    }

    /*
    用户详情接口
    */
    @RequestMapping(value = "/csutomerInfo",method = RequestMethod.POST)
    @ResponseBody
    public ConcurrentHashMap CustomerInfo(Long id,HttpSession session){
        return _customerService.CustomerInfo(id,false,session);
    }

    /*
    用户列表接口（含分页）
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public ConcurrentHashMap CustomerList(String userName,Long department_id,Integer page,Integer size){
        return _customerService.CustomerList(userName,department_id,page,size);
    }
    /*
    修改信息（昵称、邮箱、部门）
     */
    @RequestMapping(value = "",method = RequestMethod.PUT)
    @ResponseBody
    public ConcurrentHashMap UpdateCustomer(Long user_Id,String nickName,String email,Long department_id,HttpSession session) {
        return _customerService.UpdateCustomer(user_Id,nickName,email,department_id,false,session);
    }
    /*
    修改密码
     */
    @RequestMapping(value = "/{id}/reset-password",method = RequestMethod.POST)
    @ResponseBody
    public ConcurrentHashMap ResetPassword(@PathVariable("id") Long id, String newPassword, String confirmPassword) {
        return _customerService.ResetPassword(id,newPassword,confirmPassword);
    }

    /*
    删除人员
     */
    @RequestMapping(value = "",method = RequestMethod.DELETE)
    @ResponseBody
    public ConcurrentHashMap DeleteCustomer(Long id) {
        return _customerService.DeleteCustomer(id);
    }
    /*
    给人员添加角色
     */
    @RequestMapping("/addCustomerRole")
    @ResponseBody
    public ConcurrentHashMap AddCustomerRole(Long customerId,Long roleId){
        return _customerService.AddCustomerRole(customerId,roleId);
    }
}
