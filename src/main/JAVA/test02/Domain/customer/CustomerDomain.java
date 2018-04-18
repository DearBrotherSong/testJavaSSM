package test02.Domain.customer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.ui.ModelMap;
import test02.Domain.CustomerEntity;
import test02.Infrastructure.service.CustomerService;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerDomain {
    private ApplicationContext applicationContext;
    @Resource
    private CustomerService customerService;

    public CustomerService getCustomerService(){
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");//加载spring配置文件
        customerService = applicationContext.getBean(CustomerService.class);
        return customerService;
    }

    public ConcurrentHashMap Register(CustomerEntity customer){
        return getCustomerService().Register(customer);
    }

    public ConcurrentHashMap Login(String username, String password, HttpSession session){
        return getCustomerService().Login(username, password,session);
    }

    public ConcurrentHashMap CustomerInfo(Long id,boolean isCurrent,HttpSession session){
        return getCustomerService().CustomerInfo(id,isCurrent,session);
    }

    public ConcurrentHashMap CustomerList(String userName,Long department_id,Integer page,Integer size){
        return getCustomerService().CustomerList(userName,department_id,page,size);
    }

    public ConcurrentHashMap UpdateCustomer(Long CustomerId,String nickName,String email,Long department_id,boolean isPublic,HttpSession session) {
        return getCustomerService().UpdateCustomer(CustomerId,nickName,email,department_id,isPublic,session);
    }

    public ConcurrentHashMap ResetPassword(Long id,String newPassword,String confirmPassword) {
        return getCustomerService().ResetPassword(id,newPassword,confirmPassword);
    }
    public ConcurrentHashMap DeleteCustomer(Long id) {
        return getCustomerService().DeleteCustomer(id);
    }
    public ConcurrentHashMap AddCustomerRole(Long customerId,Long roleId) {
        return getCustomerService().AddCustomerRole(customerId,roleId);
    }
}
