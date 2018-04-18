package test02.Infrastructure.CommonTools;

import java.util.concurrent.ConcurrentHashMap;

/*
返回数据封装
 */
public class APIReturn {

    /*
    自定义支持
     */
    public ConcurrentHashMap apiReturn(int code){
        ConcurrentHashMap result = new ConcurrentHashMap();
        result.put("code",code);
        return result;
    }
    public ConcurrentHashMap apiReturn(int code, String message){
        ConcurrentHashMap result = new ConcurrentHashMap();
        result.put("code",code);
        result.put("message",message);
        return result;
    }
    public ConcurrentHashMap apiReturn(int code, String message,Object data){
        ConcurrentHashMap result = new ConcurrentHashMap();
        result.put("code",code);
        result.put("message",message);
        result.put("data",data);
        return result;
    }

    /*
    使用的APIReturn(已封装的返回)
     */
    public ConcurrentHashMap Success(){
        return apiReturn(CommonTool.CodeEnum.Success.getCode(),CommonTool.CodeEnum.Success.getMessage());
    }

    public ConcurrentHashMap CheckParamFaild() {
        return apiReturn(CommonTool.CodeEnum.CheckParamFaild.getCode(),CommonTool.CodeEnum.CheckParamFaild.getMessage());
    }

    public ConcurrentHashMap CheckEmailFaild() {
        return apiReturn(CommonTool.CodeEnum.EmailCheckFaild.getCode(),CommonTool.CodeEnum.EmailCheckFaild.getMessage());
    }

    public ConcurrentHashMap CheckPasswordFaild() {
        return apiReturn(CommonTool.CodeEnum.PasswordCheckFaild.getCode(),CommonTool.CodeEnum.PasswordCheckFaild.getMessage());
    }

    public ConcurrentHashMap EmailFormatWrong() {
        return apiReturn(CommonTool.CodeEnum.EmailFormatWrong.getCode(),CommonTool.CodeEnum.EmailFormatWrong.getMessage());
    }

    public ConcurrentHashMap PasswordFormatWrong() {
        return apiReturn(CommonTool.CodeEnum.PasswordFormatWrong.getCode(),CommonTool.CodeEnum.PasswordFormatWrong.getMessage());
    }

    public ConcurrentHashMap UnknownExceptionProduce() {
        return apiReturn(CommonTool.CodeEnum.UnknownExceptionProduce.getCode(),CommonTool.CodeEnum.UnknownExceptionProduce.getMessage());
    }

    public ConcurrentHashMap TokenWrong() {
        return apiReturn(CommonTool.CodeEnum.TokenWrong.getCode(),CommonTool.CodeEnum.TokenWrong.getMessage());
    }

    public ConcurrentHashMap DeptOrParentWrong() {
        return apiReturn(CommonTool.CodeEnum.DepartmentOrParentWrong.getCode(),CommonTool.CodeEnum.DepartmentOrParentWrong.getMessage());
    }
    public ConcurrentHashMap DepartmentHasCustomer() {
        return apiReturn(CommonTool.CodeEnum.DepartmentHasCustomer.getCode(),CommonTool.CodeEnum.DepartmentHasCustomer.getMessage());
    }
    public ConcurrentHashMap InitDeptOrParentWrong() {
        return apiReturn(CommonTool.CodeEnum.InitDepartmentOrParentWrong.getCode(),CommonTool.CodeEnum.InitDepartmentOrParentWrong.getMessage());
    }
    public ConcurrentHashMap CheckManagerEmailFaild() {
        return apiReturn(CommonTool.CodeEnum.ManagerEmailCheckFaild.getCode(),CommonTool.CodeEnum.ManagerEmailCheckFaild.getMessage());
    }
    public ConcurrentHashMap LogField() {
        return apiReturn(CommonTool.CodeEnum.LogField.getCode(),CommonTool.CodeEnum.LogField.getMessage());
    }
    public ConcurrentHashMap GetCustomerField() {
        return apiReturn(CommonTool.CodeEnum.NoSuchCustomer.getCode(),CommonTool.CodeEnum.NoSuchCustomer.getMessage());
    }
    public ConcurrentHashMap PasswordResetCheckFaild() {
        return apiReturn(CommonTool.CodeEnum.PasswordResetCheckFaild.getCode(),CommonTool.CodeEnum.PasswordResetCheckFaild.getMessage());
    }
    public ConcurrentHashMap PasswordResetFaild() {
        return apiReturn(CommonTool.CodeEnum.PasswordResetFaild.getCode(),CommonTool.CodeEnum.PasswordResetFaild.getMessage());
    }
    public ConcurrentHashMap LoginLost() {
        return apiReturn(CommonTool.CodeEnum.LoginLost.getCode(),CommonTool.CodeEnum.LoginLost.getMessage());
    }
    public ConcurrentHashMap TargetLost() {
        return apiReturn(CommonTool.CodeEnum.TargetLost.getCode(),CommonTool.CodeEnum.TargetLost.getMessage());
    }
    public ConcurrentHashMap RoleNameCheckFail() {
        return apiReturn(CommonTool.CodeEnum.RoleNameCheckFail.getCode(),CommonTool.CodeEnum.RoleNameCheckFail.getMessage());
    }
    public ConcurrentHashMap RoleIdCheckFail() {
        return apiReturn(CommonTool.CodeEnum.RoleIdCheckFail.getCode(),CommonTool.CodeEnum.RoleIdCheckFail.getMessage());
    }
    public ConcurrentHashMap CustomerOwnedRole() {
        return apiReturn(CommonTool.CodeEnum.CustomerOwnedRole.getCode(),CommonTool.CodeEnum.CustomerOwnedRole.getMessage());
    }
}
