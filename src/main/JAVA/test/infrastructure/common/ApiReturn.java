package test.infrastructure.common;

import java.util.concurrent.ConcurrentHashMap;

/*
返回数据封装
 */
public class ApiReturn {

    /*
    自定义支持
     */
    public ConcurrentHashMap apiReturn(int code) {
        ConcurrentHashMap result = new ConcurrentHashMap();
        result.put("code",code);
        return result;
    }

    public ConcurrentHashMap apiReturn(int code, String message) {
        ConcurrentHashMap result = new ConcurrentHashMap();
        result.put("code",code);
        result.put("message",message);
        return result;
    }

    public ConcurrentHashMap apiReturn(int code, String message,Object data) {
        ConcurrentHashMap result = new ConcurrentHashMap();
        result.put("code",code);
        result.put("message",message);
        result.put("data",data);
        return result;
    }

    /*
    使用的APIReturn(已封装的返回)
     */
    public ConcurrentHashMap success() {
        return apiReturn(CommonTool.CodeEnum.Success.getCode(),CommonTool.CodeEnum.Success.getMessage());
    }

    public ConcurrentHashMap checkParamFaild() {
        return apiReturn(CommonTool.CodeEnum.CheckParamFaild.getCode(),CommonTool.CodeEnum.CheckParamFaild.getMessage());
    }

    public ConcurrentHashMap checkEmailFaild() {
        return apiReturn(CommonTool.CodeEnum.EmailCheckFaild.getCode(),CommonTool.CodeEnum.EmailCheckFaild.getMessage());
    }

    public ConcurrentHashMap checkPasswordFaild() {
        return apiReturn(CommonTool.CodeEnum.PasswordCheckFaild.getCode(),CommonTool.CodeEnum.PasswordCheckFaild.getMessage());
    }

    public ConcurrentHashMap emailFormatWrong() {
        return apiReturn(CommonTool.CodeEnum.EmailFormatWrong.getCode(),CommonTool.CodeEnum.EmailFormatWrong.getMessage());
    }

    public ConcurrentHashMap passwordFormatWrong() {
        return apiReturn(CommonTool.CodeEnum.PasswordFormatWrong.getCode(),CommonTool.CodeEnum.PasswordFormatWrong.getMessage());
    }

    public ConcurrentHashMap unknownExceptionProduce() {
        return apiReturn(CommonTool.CodeEnum.UnknownExceptionProduce.getCode(),CommonTool.CodeEnum.UnknownExceptionProduce.getMessage());
    }

    public ConcurrentHashMap tokenWrong() {
        return apiReturn(CommonTool.CodeEnum.TokenWrong.getCode(),CommonTool.CodeEnum.TokenWrong.getMessage());
    }

    public ConcurrentHashMap deptOrParentWrong() {
        return apiReturn(CommonTool.CodeEnum.DepartmentOrParentWrong.getCode(),CommonTool.CodeEnum.DepartmentOrParentWrong.getMessage());
    }

    public ConcurrentHashMap departmentHasCustomer() {
        return apiReturn(CommonTool.CodeEnum.DepartmentHasCustomer.getCode(),CommonTool.CodeEnum.DepartmentHasCustomer.getMessage());
    }

    public ConcurrentHashMap initDeptOrParentWrong() {
        return apiReturn(CommonTool.CodeEnum.InitDepartmentOrParentWrong.getCode(),CommonTool.CodeEnum.InitDepartmentOrParentWrong.getMessage());
    }

    public ConcurrentHashMap checkManagerEmailFaild() {
        return apiReturn(CommonTool.CodeEnum.ManagerEmailCheckFaild.getCode(),CommonTool.CodeEnum.ManagerEmailCheckFaild.getMessage());
    }

    public ConcurrentHashMap logField() {
        return apiReturn(CommonTool.CodeEnum.LogField.getCode(),CommonTool.CodeEnum.LogField.getMessage());
    }

    public ConcurrentHashMap getCustomerField() {
        return apiReturn(CommonTool.CodeEnum.NoSuchCustomer.getCode(),CommonTool.CodeEnum.NoSuchCustomer.getMessage());
    }

    public ConcurrentHashMap passwordResetCheckFaild() {
        return apiReturn(CommonTool.CodeEnum.PasswordResetCheckFaild.getCode(),CommonTool.CodeEnum.PasswordResetCheckFaild.getMessage());
    }

    public ConcurrentHashMap passwordResetFaild() {
        return apiReturn(CommonTool.CodeEnum.PasswordResetFaild.getCode(),CommonTool.CodeEnum.PasswordResetFaild.getMessage());
    }

    public ConcurrentHashMap loginLost() {
        return apiReturn(CommonTool.CodeEnum.LoginLost.getCode(),CommonTool.CodeEnum.LoginLost.getMessage());
    }

    public ConcurrentHashMap targetLost() {
        return apiReturn(CommonTool.CodeEnum.TargetLost.getCode(),CommonTool.CodeEnum.TargetLost.getMessage());
    }

    public ConcurrentHashMap roleNameCheckFail() {
        return apiReturn(CommonTool.CodeEnum.RoleNameCheckFail.getCode(),CommonTool.CodeEnum.RoleNameCheckFail.getMessage());
    }

    public ConcurrentHashMap roleIdCheckFail() {
        return apiReturn(CommonTool.CodeEnum.RoleIdCheckFail.getCode(),CommonTool.CodeEnum.RoleIdCheckFail.getMessage());
    }

    public ConcurrentHashMap customerOwnedRole() {
        return apiReturn(CommonTool.CodeEnum.CustomerOwnedRole.getCode(),CommonTool.CodeEnum.CustomerOwnedRole.getMessage());
    }
}
