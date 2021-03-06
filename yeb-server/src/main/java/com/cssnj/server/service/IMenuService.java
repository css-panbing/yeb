package com.cssnj.server.service;

import com.cssnj.server.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cssnj.server.pojo.Role;

import java.util.List;

/**
 * 菜单接口
 *
 * @author panbing
 * @since 2021-12-16
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 根据用户ID查询菜单列表
     * @return
     */
    List<Menu> getMenusByAdminId();

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
