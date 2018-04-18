package test02.Application;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import test02.Domain.customer.CustomerDomain;
import test02.Domain.CustomerEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import test02.Infrastructure.CommonTools.APIReturn;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/private/users")
public class CustomerController{

    /*
    注册接口（添加用户）
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ConcurrentHashMap Regist(String customerJson){
        CustomerEntity customer = JSON.parseObject(customerJson,CustomerEntity.class);
        CustomerDomain resgister = new CustomerDomain();
        return resgister.Register(customer);
    }

    /*
    用户详情接口
    */
    @RequestMapping(value = "/csutomerInfo",method = RequestMethod.POST)
    @ResponseBody
    public ConcurrentHashMap CustomerInfo(Long id,HttpSession session){
        CustomerDomain customer = new CustomerDomain();
        return customer.CustomerInfo(id,false,session);
    }

    /*
    用户列表接口（含分页）
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public ConcurrentHashMap CustomerList(String userName,Long department_id,Integer page,Integer size){
        CustomerDomain customer = new CustomerDomain();
        return customer.CustomerList(userName,department_id,page,size);
    }
    /*
    修改信息（昵称、邮箱、部门）
     */
    @RequestMapping(value = "",method = RequestMethod.PUT)
    @ResponseBody
    public ConcurrentHashMap UpdateCustomer(Long user_Id,String nickName,String email,Long department_id,HttpSession session) {
        CustomerDomain customer = new CustomerDomain();
        return customer.UpdateCustomer(user_Id,nickName,email,department_id,false,session);
    }
    /*
    修改密码
     */
    @RequestMapping(value = "/{id}/reset-password",method = RequestMethod.POST)
    @ResponseBody
    public ConcurrentHashMap ResetPassword(@PathVariable("id") Long id, String newPassword, String confirmPassword) {
        CustomerDomain customer = new CustomerDomain();
        return customer.ResetPassword(id,newPassword,confirmPassword);
    }

    /*
    删除人员
     */
    @RequestMapping(value = "",method = RequestMethod.DELETE)
    @ResponseBody
    public ConcurrentHashMap DeleteCustomer(Long id) {
        CustomerDomain customer = new CustomerDomain();
        return customer.DeleteCustomer(id);
    }
    /*
    给人员添加角色
     */
    @RequestMapping("/addCustomerRole")
    @ResponseBody
    public ConcurrentHashMap AddCustomerRole(Long customerId,Long roleId){
        CustomerDomain customer = new CustomerDomain();
        return customer.AddCustomerRole(customerId,roleId);
    }
}
