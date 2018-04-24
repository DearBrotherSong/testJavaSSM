package test02.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import test02.Infrastructure.CommonTools.APIReturn;
import test02.Infrastructure.service.CustomerService;
import test02.thrift.ClientProxy;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/public/users/current")
public class CustomerPublicController {

    private CustomerService _customerService = null;

    @Autowired
    public CustomerPublicController(CustomerService customerService) {
        this._customerService = customerService;
    }
    /*
    登录接口
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public ConcurrentHashMap Login(String username, String password, HttpSession session){
        return _customerService.Login(username,password,session);
    }
    /*
    当前登录人信息
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ConcurrentHashMap CustomerInfo(HttpSession session){
        return _customerService.CustomerInfo((long)0,true,session);
    }
    /*
    修改信息（昵称、邮箱）
     */
    @RequestMapping(value = "",method = RequestMethod.PUT)
    @ResponseBody
    public ConcurrentHashMap UpdateCustomer(Long user_Id,String nickName,String email,HttpSession session) {
        return _customerService.UpdateCustomer(user_Id,nickName,email,(long)0,true,session);
    }
    /*
    注销登录
     */
    @RequestMapping("/logout")
    @ResponseBody
    public ConcurrentHashMap Logout(HttpSession session) {
        session.invalidate();
        return new APIReturn().Success();
    }
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
    public String Test(String param) throws Exception {
        ClientProxy clientProxy = new ClientProxy();
        return clientProxy.start(param);
    }
}

