package test02.Infrastructure.sql;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import test02.Data.CustomerRoleEntity;

@Repository
public interface CustomerRoleMapper {
    public CustomerRoleEntity getByCustomerIdAndRoleId(CustomerRoleEntity customerRoleEntity);
    public void deleteByCustomerIdAndRoleId(CustomerRoleEntity customerRoleEntity);
    public int insert(CustomerRoleEntity customerRoleEntity);
    public int deleteByCustomerId(@Param("customerId") Long customerId);
    public void deleteByRoleId(@Param("roleId") Long roleId);

}
