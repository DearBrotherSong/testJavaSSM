package test.data;

import java.io.Serializable;

public class CustomerRoleEntity implements Serializable {
    private Long _customerId;

    private Long _roleId;

    public Long getCustomerId() {
        return _customerId;
    }

    public void setCustomerId(Long customerId) {
        this._customerId = customerId;
    }

    public Long getRoleId() {
        return _roleId;
    }

    public void setRoleId(Long roleId) {
        this._roleId = roleId;
    }
}
