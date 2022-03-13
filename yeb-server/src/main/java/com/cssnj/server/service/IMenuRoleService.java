package com.cssnj.server.service;

import com.cssnj.server.common.response.ResponseData;
import com.cssnj.server.pojo.Menu;
import com.cssnj.server.pojo.MenuRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  角色菜单关联类
 *
 * @author panbing
 * @since 2021-12-16
 */
public interface IMenuRoleService extends IService<MenuRole> {

    /**
     * 更新角色关联菜单
     * @param rid
     * @param mids
     * @return
     */
    ResponseData updateMenuRole(Integer rid, Integer[] mids);

}
