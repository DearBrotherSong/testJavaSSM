package test.data;

import java.io.Serializable;
import java.sql.Timestamp;

public class RoleEntity implements Serializable {
    private Long _id;
    private String _name;
    private String _description;
    private Timestamp _createTime;
    private int _state;


    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        this._id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        this._description = description;
    }

    public Timestamp getCreateTime() {
        return _createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this._createTime = createTime;
    }

    public int getState() {
        return _state;
    }

    public void setState(int state) {
        this._state = state;
    }
}
