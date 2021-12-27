package com.cssnj.server.mapper;

import com.cssnj.server.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色 Mapper 接口
 *
 * @author panbing
 * @since 2021-12-16
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查询角色列表
     * @param adminId
     * @return
     */
    List<Role> getRolesWithAdminId(Integer adminId);
}
