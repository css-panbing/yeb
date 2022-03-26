package com.cssnj.server.mapper;

import com.cssnj.server.pojo.AdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 操作员 Mapper接口
 *
 * @author panbing
 * @since 2021-12-16
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /**
     * 插入操作员关联角色信息
     * @param adminId
     * @param roleIds
     * @return 返回插入成功条数
     */
    Integer insertAdminRoles(@Param("adminId") Integer adminId, @Param("roleIds") String[] roleIds);
}
