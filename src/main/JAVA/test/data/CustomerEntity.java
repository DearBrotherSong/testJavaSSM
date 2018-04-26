package test.data;

import java.io.Serializable;
import java.sql.Timestamp;

//用户实体类
public class CustomerEntity implements Serializable{
    private Long id;
    private String userName;
    private String nickName;
    private String email;
    private String password;
    private Timestamp creation_time;
    private Timestamp last_loin_time;
    private int state;
    private Long department_id;
    private String department_Name;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(Timestamp creation_time) {
        this.creation_time = creation_time;
    }

    public Timestamp getLast_loin_time() {
        return last_loin_time;
    }

    public void setLast_loin_time(Timestamp last_loin_time) {
        this.last_loin_time = last_loin_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        state = state;
    }

    public Long getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(long department_id) {
        this.department_id = department_id;
    }

    public String getDepartment_Name() {
        return department_Name;
    }

    public void setDepartment_Name(String department_Name) {
        this.department_Name = department_Name;
    }
}
