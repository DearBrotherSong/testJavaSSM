package test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import test.infrastructure.common.ApiReturn;
import test.infrastructure.service.CustomerService;

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
    public ConcurrentHashMap login(String username, String password, HttpSession session) {
        return _customerService.login(username,password,session);
    }

    /*
    当前登录人信息
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public ConcurrentHashMap customerInfo(HttpSession session) {
        return _customerService.customerInfo((long)0,true,session);
    }

    /*
    修改信息（昵称、邮箱）
     */
    @RequestMapping(value = "",method = RequestMethod.PUT)
    @ResponseBody
    public ConcurrentHashMap updateCustomer(Long userId,String nickName,String email,HttpSession session) {
        return _customerService.updateCustomer(userId,nickName,email,(long)0,true,session);
    }

    /*
    注销登录
     */
    @RequestMapping("/logout")
    @ResponseBody
    public ConcurrentHashMap logout(HttpSession session) {
        session.invalidate();
        return new ApiReturn().success();
    }

}

