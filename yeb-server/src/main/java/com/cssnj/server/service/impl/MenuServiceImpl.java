package com.cssnj.server.service.impl;

import com.cssnj.server.common.utils.AdminUtils;
import com.cssnj.server.common.utils.MenuTreeNodeUtil;
import com.cssnj.server.pojo.Admin;
import com.cssnj.server.pojo.Menu;
import com.cssnj.server.mapper.MenuMapper;
import com.cssnj.server.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  菜单服务实现类
 * </p>
 *
 * @author panbing
 * @since 2021-12-16
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 通过用户ID获取菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenusByAdminId() {
        Admin admin = AdminUtils.getCurrentAdmin();
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        // 从Redis中获取菜单数据
        List<Menu> menus = (List<Menu>) valueOperations.get("menu_"+admin.getId());
        // 如果Redis中没有获取到则去数据库中查询
        if(CollectionUtils.isEmpty(menus)){
            menus = menuMapper.getMenuByAdminId(admin.getId());//通过用户ID获取菜单列表（不推荐：通过表关联只能查询固定层级菜单树）
            //menus = menuMapper.getMenusByAdminId(admin.getId());//通过用户ID获取菜单列表（推荐：调用向下递归方法查询菜单树）,需要将查询结果加工成树状结构
            //menus = MenuTreeNodeUtil.getChildrenNode(1, menus);//问题：这种加工方式只方便加工有一个最高节点的情况，多个最高平级节点无法加工

            // 将菜单数据存入到Redis中
            valueOperations.set("menu_"+admin.getId(), menus);
        }
        return menus;
    }

    /**
     * 根据角色获取菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenusWithRole() {
        List<Menu> menus = menuMapper.getMenusWithRole();
        return menus;
    }

    /**
     * 查询所有菜单
     * @return
     */
    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

}
