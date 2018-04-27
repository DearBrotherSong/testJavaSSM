package test.data;

import java.io.Serializable;
import java.sql.Timestamp;

public class DepartmentEntity implements Serializable {
    private Long _id;
    private String _name;
    private Long _parentId;
    private String _idPath;
    private String _namePath;
    private Timestamp _createTime;
    private String _description;
    private String _manager;
    private int _state;

    public Long getId() {
        return _id;
    }

    public void setId(long id) {
        this._id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public Long getParentId() {
        return _parentId;
    }

    public void setParentId(long parentId) {
        this._parentId = parentId;
    }

    public String getIdPath() {
        return _idPath;
    }

    public void setIdPath(String idPath) {
        this._idPath = idPath;
    }

    public String getnamePath() {
        return _namePath;
    }

    public void setnamePath(String namePath) {
        this._namePath = namePath;
    }

    public Timestamp getCreateTime() {
        return _createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this._createTime = createTime;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        this._description = description;
    }

    public String getManager() {
        return _manager;
    }

    public void setManager(String manager) {
        this._manager = manager;
    }

    public int getState() {
        return _state;
    }

    public void setState(int state) {
        this._state = state;
    }

}