package test.infrastructure.sql;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import test.data.CustomerEntity;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Repository
public interface CustomerMapper {
    public List<HashMap> list(@Param("userName") String userName,@Param("idPath") String idPath, @Param("offset") int offset,@Param("limit") int limit);

    public int getListTotal(@Param("userName") String userName,@Param("idPath") String idPath, @Param("offset") int offset,@Param("limit") int limit);

    public CustomerEntity getById(@Param("id")Long id);

    public HashMap getUserMessDetailById(Long id);

    public CustomerEntity getByUserName(String username);

    public int updateLastLoginTime(@Param("loginTime")Timestamp loginTime,@Param("id")Long id);

    public CustomerEntity getBasicByEmail(@Param("email")String email);

    public CustomerEntity getBasicByEmailOrName(@Param("email")String email, @Param("userName")String userName,@Param("nickName")String nickName,@Param("id")Long id);

    public int updateCustomerBasic(@Param("departmentId")Long departmentId, @Param("email")String email,@Param("nickName")String nickName,@Param("id")Long id);

    public int updatePassword(@Param("id")Long id,@Param("newPass")String newPass);

    public Long register(CustomerEntity customer);

    public List<CustomerEntity> getByDepartmentIds(@Param("deptIds")List<Long> deptIds);

    public void deleteCustomer(Long id);
}
