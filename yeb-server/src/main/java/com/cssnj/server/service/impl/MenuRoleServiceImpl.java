package com.cssnj.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cssnj.server.common.response.RespData;
import com.cssnj.server.pojo.MenuRole;
import com.cssnj.server.mapper.MenuRoleMapper;
import com.cssnj.server.service.IMenuRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *  角色菜单关联 实现类
 *
 * @author panbing
 * @since 2021-12-16
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    /**
     * 更新角色菜单(多条语句要加事务处理)
     * @param rid 角色id
     * @param mids 菜单ids
     * @return
     */
    @Override
    @Transactional
    public RespData updateMenuRole(Integer rid, String[] mids) {
        //1、通过角色id删除相关记录
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid", rid));
        if(mids == null || mids.length == 0){
            return RespData.success("更新成功");
        }
        //2、重新添加
        Integer result = menuRoleMapper.insertList(rid, mids);
        if(result == mids.length){
            return RespData.success("更新成功");
        }
        return RespData.error("更新失败");
    }

}
