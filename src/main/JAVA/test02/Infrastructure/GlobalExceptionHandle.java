package test02.Infrastructure;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import test02.Infrastructure.CommonTools.APIReturn;

import java.util.concurrent.ConcurrentHashMap;

@ControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ConcurrentHashMap handleException(){
        return new APIReturn().UnknownExceptionProduce();
    }
}
