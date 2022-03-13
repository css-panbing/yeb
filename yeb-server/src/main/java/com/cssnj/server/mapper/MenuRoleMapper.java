package com.cssnj.server.mapper;

import com.cssnj.server.pojo.Menu;
import com.cssnj.server.pojo.MenuRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  菜单角色 Mapper 接口
 *
 * @author panbing
 * @since 2021-12-16
 */
@Repository
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    /**
     * 批量添加菜单角色关联信息
     * @param rid 角色id
     * @param mids 菜单id
     * @return 返回成功条数
     */
    Integer insertList(@Param("rid") Integer rid, @Param("mids") Integer[] mids);

}
