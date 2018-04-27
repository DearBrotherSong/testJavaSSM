package test.infrastructure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.data.RoleEntity;
import test.infrastructure.common.ApiReturn;
import test.infrastructure.common.CommonTool;
import test.infrastructure.sql.CustomerRoleMapper;
import test.infrastructure.sql.RoleMapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoleService {
    @Autowired
    private RoleMapper _roleMapper;
    @Autowired
    private CustomerRoleMapper _customerRoleMapper;

    public RoleService(CustomerRoleMapper customerRoleMapper,RoleMapper roleMapper) {
        this._customerRoleMapper = customerRoleMapper;
        this._roleMapper = roleMapper;
    }

    /*
    添加角色
     */
    public ConcurrentHashMap addRole(String name,String description) {
        if (CommonTool.Tools.isNullOrWhiteSpace(name)
                || name.length() > 50
                || (!CommonTool.Tools.isNullOrWhiteSpace(name) && description.length() > 200)) {
            return new ApiReturn().checkParamFaild();
        }


        if (_roleMapper.getByName(name,(long)0) != null) {
            return new ApiReturn().roleNameCheckFail();
        }

        RoleEntity role = new RoleEntity();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        role.setName(name);
        role.setCreateTime(now);
        role.setDescription(description);
        role.setState(CommonTool.State.StateOn.getState());

        try {
            _roleMapper.addRole(role);
        } catch (Exception e) {
            return new ApiReturn().unknownExceptionProduce();
        }
        return new ApiReturn().success();
    }

    /*
    修改角色
     */
    public ConcurrentHashMap updateRole(Long id,String name,String description)
    {
        if (CommonTool.Tools.isNullOrWhiteSpace(name)
                || name.length() > 50
                || (!CommonTool.Tools.isNullOrWhiteSpace(name) && description.length() > 200)) {
            return new ApiReturn().checkParamFaild();
        }

        RoleEntity currentRole = _roleMapper.getById(id);
        if (currentRole == null) {
            return new ApiReturn().roleIdCheckFail();
        }
        if (_roleMapper.getByName(name,id) != null) {
            return new ApiReturn().roleNameCheckFail();
        }
        try {
            currentRole.setName(name);
            currentRole.setDescription(description);
            _roleMapper.updateById(currentRole);
        } catch (Exception e) {
            return new ApiReturn().unknownExceptionProduce();
        }
        return new ApiReturn().success();

    }

    /*
    删除角色
     */
    @Transactional
    public ConcurrentHashMap deleteRole(Long id) {
        RoleEntity currentRole = _roleMapper.getById(id);
        if (currentRole == null) {
            return new ApiReturn().roleIdCheckFail();
        }

        try {
            _customerRoleMapper.deleteByRoleId(id);
            _roleMapper.deleteById(id);
        } catch (Exception e) {
            return new ApiReturn().unknownExceptionProduce();
        }
        return new ApiReturn().success();
    }

    /*
    角色列表
     */
    public ConcurrentHashMap roleList()
    {
        List<RoleEntity> roleList = _roleMapper.findAll();
        return new ApiReturn().apiReturn(CommonTool.CodeEnum.Success.getCode(),"",roleList);
    }

    /*
    角色详情
     */
    public ConcurrentHashMap roleInfo(Long id) {
        RoleEntity roleInfo = _roleMapper.getById(id);
        if (roleInfo == null) {
            return new ApiReturn().roleIdCheckFail();
        }
        return new ApiReturn().apiReturn(CommonTool.CodeEnum.Success.getCode(),"",roleInfo);

    }
}
