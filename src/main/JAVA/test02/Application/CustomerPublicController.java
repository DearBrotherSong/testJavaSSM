package test02.Application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import test02.Domain.customer.CustomerDomain;
import test02.Infrastructure.CommonTools.APIReturn;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/public/users/current")
public class CustomerPublicController {
    /*
    登录接口
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public ConcurrentHashMap Login(String username, String password, HttpSession session){
        CustomerDomain login = new CustomerDomain();
        return login.Login(username,password,session);
    }
    /*
    当前登录人信息
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ConcurrentHashMap CustomerInfo(HttpSession session){
        CustomerDomain customer = new CustomerDomain();
        return customer.CustomerInfo((long)0,true,session);
    }
    /*
    修改信息（昵称、邮箱）
     */
    @RequestMapping(value = "",method = RequestMethod.PUT)
    @ResponseBody
    public ConcurrentHashMap UpdateCustomer(Long user_Id,String nickName,String email,HttpSession session) {
        CustomerDomain customer = new CustomerDomain();
        return customer.UpdateCustomer(user_Id,nickName,email,(long)0,true,session);
    }
    /*
    注销登录
     */
    @RequestMapping("/logout")
    @ResponseBody
    public ConcurrentHashMap Logout(HttpSession session) {
        CustomerDomain customer = new CustomerDomain();
        session.invalidate();
        return new APIReturn().Success();
    }
}

