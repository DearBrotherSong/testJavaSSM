package test02.Infrastructure.sql;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import test02.Domain.RoleEntity;

import java.util.List;


@Repository
public interface RoleMapper {
    public RoleEntity getById(Long id);
    public List<String> getRoleNamesByCustomerId(Long customerId);
    public RoleEntity getByName(@Param("name") String name,@Param("id")Long id);
    public void addRole(RoleEntity role);
    public void deleteById(Long id);
    public void updateById(RoleEntity role);
    public List<RoleEntity> findAll();
}
