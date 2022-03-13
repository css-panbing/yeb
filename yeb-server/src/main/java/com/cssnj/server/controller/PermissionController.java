package com.cssnj.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cssnj.server.common.response.ResponseData;
import com.cssnj.server.pojo.Menu;
import com.cssnj.server.pojo.MenuRole;
import com.cssnj.server.pojo.Role;
import com.cssnj.server.service.IMenuRoleService;
import com.cssnj.server.service.IMenuService;
import com.cssnj.server.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限管理
 * @author panbing
 * @date 2021/12/28 20:35
 */
@RestController
@RequestMapping("/system/basic/permission")
@Api(tags = "权限管理")
public class PermissionController {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IMenuRoleService menuRoleService;

    @ApiOperation("获取所有角色")
    @GetMapping("/role/")
    public List<Role> getAllRoles(){
        return roleService.list();
    }

    @ApiOperation("添加角色")
    @PostMapping("/role/")
    public ResponseData addRole(@RequestBody Role role){
        if(!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        if(roleService.save(role)){
           return ResponseData.success("添加成功");
        }
        return ResponseData.error("添加失败");
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/role/{rid}")
    public ResponseData deleteRole(@PathVariable Integer rid){
        if(roleService.removeById(rid)){
            return ResponseData.success("删除成功");
        }
        return ResponseData.error("删除失败");
    }

    @ApiOperation("查询所有菜单")
    @GetMapping("/role/menus/")
    public List<Menu> getAllMenus(){
        return menuService.getAllMenus();
    }

    @ApiOperation("根据角色id查询所有菜单id")
    @GetMapping("/role/menus/{rid}")
    public List<Integer> getAllMenusByRole(@PathVariable Integer rid){
        List<MenuRole> list = menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid", rid));
        return list.stream().map(MenuRole::getMid).collect(Collectors.toList());
    }

    @ApiOperation("更新角色关联菜单")
    @PutMapping("/role/")
    public ResponseData updateRole(Integer rid, Integer [] mids){
        return menuRoleService.updateMenuRole(rid, mids);
    }

}
