package com.cssnj.server.mapper;

import com.cssnj.server.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  菜单 Mapper 接口
 *
 * @author panbing
 * @since 2021-12-16
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 通过用户ID获取菜单列表
     * @param id
     * @return
     */
    List<Menu> getMenuByAdminId(Integer id);

    /**
     * 根据角色获取菜单列表
     * @return
     */
    List<Menu> getMenusWithRole();

    /**
     * 查询所有菜单
     * @return
     */
    List<Menu> getAllMenus();
}
