package test02.Infrastructure.CommonTools;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

//公共工具类
public class CommonTool{

    /*
    公共工具
     */
    public static class Tools{
        /*
        MD5算法，用于处理密码
         */
        public static String GetMD5(String data) {
            return DigestUtils.md5Hex(data);
        }

        /*
        验证邮箱格式（简单规则）
         */
        public static boolean CheckEmailFormat(String email)
        {
            if(isNullOrWhiteSpace(email))
                return false;
            //规则：大小写字母或数字记为X。 X（至少一次） + @ + X（至少一次）+ （-X）（0次或1次）+ .+ X（非数字）（2次以上）
            String format = "^([a-zA-Z0-9_-])+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?.)+[a-zA-Z]{2,}$";
            return email.matches(format);
        }

        /*
        空验证
         */
        public static boolean isNullOrWhiteSpace(String data)
        {
            if(data == null)
                return true;
            if(data.trim().equals(""))
                return true;
            return false;
        }

        /*
        字符串拼接工具
         */
        public static String stringConcat(String... params)
        {
            String result = "";
            for (String item:params) {
                result = result.concat(item);
            }
            return result;
        }

        /*
        实体类转ConcurrentHashMap
         */
        public static ConcurrentHashMap entityToMap(Object entity){
            ConcurrentHashMap result = new ConcurrentHashMap();
            try {
                for (Field field : entity.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    result.put(field.getName(),field.get(entity));
                }

            }catch (Exception e){}
            return result;
        }

        public static ConcurrentHashMap<String,String> GetPropertiesMap(String propertiesName)
        {
            ConcurrentHashMap<String,String> result = new ConcurrentHashMap<String, String>();
            try {
                Properties properties = new Properties();

                InputStream stream = CommonTool.class.getClassLoader().getResourceAsStream(propertiesName);
                properties.load(stream);
                Enumeration key = properties.propertyNames();

                while(key.hasMoreElements()) {
                    String thisKey = key.nextElement().toString();
                    result.put(thisKey,properties.getProperty(thisKey));
                }

                return result;
            }catch (Exception e) {
                return null;
            }

        }

    }

    /*
    返回提示表
     */
    public static enum  CodeEnum{
        //公共
        TokenWrong("系统错误（token错误）", 500),
        Success("操作成功", 1),
        CheckParamFaild("参数错误(为空或长度错误)",2),
        UnknownExceptionProduce("操作失败，未知的系统错误", 3),

        //人员
        LogField("用户名或密码错误", 4),
        EmailCheckFaild("邮箱(用户名、昵称)已存在", 5),
        EmailFormatWrong("邮箱格式错误", 6),
        PasswordCheckFaild("密码错误",7),
        PasswordFormatWrong("密码格式错误（只含有字母和字符串，长度6-20）",8),
        NoSuchCustomer("获取用户信息失败(已删除或部门关系错误)", 9),
        PasswordResetCheckFaild("密码规范错误（只含有字母和字符串，长度6-20），或确认密码失败", 10),
        PasswordResetFaild("密码修改失败（登录失效或未知的异常，请重新登录或稍后再试），或确认密码失败", 11),
        LoginLost("未登录或登录失效，请重新登录",12),
        TargetLost("目标已失效",13),

        //部门
        DepartmentOrParentWrong("部门或上级部门不存在",14),
        DepartmentHasCustomer("该部门下存在人员，无法删除",15),
        InitDepartmentOrParentWrong("初始化错误，根节点已存在",16),
        ManagerEmailCheckFaild("主管邮箱错误", 17),

        //角色
        RoleNameCheckFail("角色名称已存在",18),
        RoleIdCheckFail("角色ID错误（不存在或已删除）",19),
        CustomerOwnedRole("该人员已是所添加的角色，无需重复添加",20);





        // 成员变量
        private String message;
        private int code;
        // 构造方法
        private CodeEnum(String message, int code) {
            this.message = message;
            this.code = code;
        }

        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public int getCode() {
            return code;
        }
        public void setCode(int code) {
            this.code = code;
        }
    }
    /*
    数据状态（0：启用；1：禁用）
     */
    public static enum  State{
        StateOn(0),StateOff(1);

        private int state;
        // 构造方法
        private State(int state) {
            this.state = state;
        }
        public int getState() {
            return state;
        }
        public void setState(int state) {
            this.state = state;
        }
    }
    /*
    数据状态（0：启用；1：禁用）
     */
    public static enum  SessionTime{
        Default(30);

        private int time;
        // 构造方法
        private SessionTime(int time) {
            this.time = time;
        }
        public int getDefault() { return time; }
        public void setDefault(int time) {
            this.time = time;
        }
    }
}