package test.infrastructure.sql;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import test.data.DepartmentEntity;

import java.util.List;

@Repository
public interface DepartmentMapper {

    public List<DepartmentEntity> findAllAsList();

    public DepartmentEntity getByParentId(@Param("parentId")Long parentId);

    public DepartmentEntity getDepartmentById(@Param("id") Long id);

    public List<DepartmentEntity> getDepartmentByIdPath(@Param("idPath") String idPath);

    public void addDepartment(DepartmentEntity dept);

    public void updateDepartment(@Param("id")Long id,@Param("name")String name,@Param("manager")String manager,@Param("description")String description);

    public void updateDepartmentPath(DepartmentEntity dept);

    public void deleteDepartment(@Param("deptIds")List<Long> id);

    public int updateChildNamePath(@Param("oldName")String oldName,@Param("newName") String newName,@Param("deptIds")List<Long> deptIds);
}
