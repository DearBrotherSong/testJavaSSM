package test02.Controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception ex){
        return "我的天呐！！！";
    }

}
