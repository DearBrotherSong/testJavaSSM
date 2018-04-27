package test.data;

import java.io.Serializable;
import java.sql.Timestamp;

//用户实体类
public class CustomerEntity implements Serializable {
    private Long _id;
    private String _userName;
    private String _nickName;
    private String _email;
    private String _password;
    private Timestamp _creationTime;
    private Timestamp _lastLoinTime;
    private int _state;
    private Long _departmentId;
    private String _departmentName;

    public Long getId() {
        return _id;
    }

    public void setId(long id) {
        this._id = id;
    }

    public String getUserName() {
        return _userName;
    }

    public void setUserName(String userName) {
        this._userName = userName;
    }

    public String getNickName() {
        return _nickName;
    }

    public void setNickName(String nickName) {
        this._nickName = nickName;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String email) {
        this._email = email;
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password) {
        this._password = password;
    }

    public Timestamp getCreationTime() {
        return _creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this._creationTime = creationTime;
    }

    public Timestamp getLastLoinTime() {
        return _lastLoinTime;
    }

    public void setLastLoinTime(Timestamp lastLoinTime) {
        this._lastLoinTime = lastLoinTime;
    }

    public int getState() {
        return _state;
    }

    public void setState(int state) {
        this._state = state;
    }

    public Long getDepartmentId() {
        return _departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this._departmentId = departmentId;
    }

    public String getDepartmentName() {
        return _departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this._departmentName = departmentName;
    }
}
