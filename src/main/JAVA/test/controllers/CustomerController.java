package test.controllers;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import test.data.CustomerEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import test.infrastructure.service.CustomerService;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/private/users")
public class CustomerController {

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
    public ConcurrentHashMap regist(String customerJson) {
        CustomerEntity customer = JSON.parseObject(customerJson,CustomerEntity.class);
        return _customerService.Register(customer);
    }

    /*
    用户详情接口
    */
    @RequestMapping(value = "/csutomerInfo",method = RequestMethod.POST)
    @ResponseBody
    public ConcurrentHashMap customerInfo(Long id,HttpSession session) {
        return _customerService.CustomerInfo(id,false,session);
    }

    /*
    用户列表接口（含分页）
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public ConcurrentHashMap customerList(String userName,Long departmentId,Integer page,Integer size) {
        return _customerService.CustomerList(userName,departmentId,page,size);
    }

    /*
    修改信息（昵称、邮箱、部门）
     */
    @RequestMapping(value = "",method = RequestMethod.PUT)
    @ResponseBody
    public ConcurrentHashMap updateCustomer(Long userId,String nickName,String email,Long departmentId,HttpSession session) {
        return _customerService.UpdateCustomer(userId,nickName,email,departmentId,false,session);
    }

    /*
    修改密码
     */
    @RequestMapping(value = "/{id}/reset-password",method = RequestMethod.POST)
    @ResponseBody
    public ConcurrentHashMap resetPassword(@PathVariable("id") Long id, String newPassword, String confirmPassword) {
        return _customerService.ResetPassword(id,newPassword,confirmPassword);
    }

    /*
    删除人员
     */
    @RequestMapping(value = "",method = RequestMethod.DELETE)
    @ResponseBody
    public ConcurrentHashMap deleteCustomer(Long id) {
        return _customerService.DeleteCustomer(id);
    }

    /*
    给人员添加角色
     */
    @RequestMapping("/addCustomerRole")
    @ResponseBody
    public ConcurrentHashMap addCustomerRole(Long customerId,Long roleId) {
        return _customerService.AddCustomerRole(customerId,roleId);
    }
}
