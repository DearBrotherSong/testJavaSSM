package test.infrastructure;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import test.infrastructure.common.CommonTool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class HandlerInterceptorOwn implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        HttpSession session = httpServletRequest.getSession();

        //三方token拦截
        String token = httpServletRequest.getParameter("token");
        if (CommonTool.Tools.isNullOrWhiteSpace(token) || session.getAttribute(token) == null) {
            httpServletResponse.sendError(CommonTool.CodeEnum.TokenWrong.getCode(),CommonTool.CodeEnum.TokenWrong.getMessage());
            return false;
        }
        boolean hasRoot = rootCheck(httpServletRequest,session,token);
        if (!hasRoot) {
            httpServletResponse.sendError(CommonTool.CodeEnum.TokenWrong.getCode(),CommonTool.CodeEnum.TokenWrong.getMessage());
        }
        return hasRoot;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object, Exception exception) throws Exception {

    }

    /*
    权限验证
     */
    public boolean rootCheck(HttpServletRequest httpServletRequest,HttpSession session,String token) {
        String url = httpServletRequest .getRequestURL().toString();
        List<String> roleList = (List<String>)session.getAttribute(token.concat("_roleList"));
        if (roleList == null) {
            return false;
        }
        String method = httpServletRequest.getMethod();
        if (url.contains("/users")) {
            //bg.角色都能查询人员列表
            if (method.equals("GET")) {
                return roleList.size() > 0;
            }

            return roleList.indexOf("bg.admin_user") >= 0;
        } else if (url.contains("/departments")) {
            //bg.角色都能查询部门列表
            if (method.equals("GET")) {
                return roleList.size() > 0;
            }

            return  roleList.indexOf("bg.admin_department") >= 0;

        } else if (url.contains("/roles")) {
            //bg.admin_role + bg.admin_user可以获取角色列表
            if (method.equals("GET") && url.contains("/role/") && Character.isDigit(url.charAt(url.length() - 1))) {
                return roleList.indexOf("bg.admin_role") >= 0 && roleList.indexOf("bg.admin_user") >= 0;
            }

            return roleList.indexOf("bg.admin_role") >= 0;
        }
        return true;
    }
}
