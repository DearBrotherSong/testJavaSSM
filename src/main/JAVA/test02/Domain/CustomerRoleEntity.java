package test02.Domain;

import java.io.Serializable;

public class CustomerRoleEntity implements Serializable {
    private Long customerId;

    private Long roleId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
