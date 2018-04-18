package sql.Mappers;

import test02.Domain.department.model.DepartmentEntity;

public interface DepartmentMapper {

    public int checkDepartmentById(Long id);
    public int checkByParentId(Long parentId);
    public int addDepartment(DepartmentEntity dept);
}
