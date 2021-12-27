package com.cssnj.server.service;

import com.cssnj.server.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 菜单
 *
 * @author panbing
 * @since 2021-12-16
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 根据用户ID查询菜单列表
     * @return
     */
    List<Menu> getMenuByAdminId();
}
