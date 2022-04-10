package com.cssnj.server.controller;


import com.cssnj.server.common.response.RespData;
import com.cssnj.server.pojo.Admin;
import com.cssnj.server.pojo.Role;
import com.cssnj.server.service.IAdminService;
import com.cssnj.server.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 操作员管理
 *
 * @author panbing
 * @since 2021-12-16
 */
@RestController
@Api(tags = "操作员管理")
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IRoleService roleService;

    @ApiOperation("根据条件获取操作员")
    @GetMapping("/")
    public List<Admin> getAdminsByKeywords(Admin admin){
        return adminService.getAdminsByKeywords(admin.getName());
    }

    @ApiOperation("获取所有角色信息")
    @GetMapping("/roles/")
    public List<Role> getAllRoles(){
        return roleService.list();
    }

    @ApiOperation("更新操作员基本信息")
    @PutMapping("/")
    public RespData updateAdmin(@RequestBody Admin admin){
        if(adminService.updateById(admin)){
            return RespData.success("更新成功");
        }
        return RespData.error("更新失败");
    }

    @ApiOperation("删除操作员信息")
    @DeleteMapping("/{id}")
    public RespData deleteAdmin(@PathVariable Integer id){
        if(adminService.removeById(id)){
            return RespData.success("删除成功");
        }
        return RespData.error("删除失败");
    }

    @ApiOperation("更新操作员角色")
    @PutMapping("/adminRoles")
    public RespData updateAdminRoles(Integer adminId, String rids){
        String [] roleIds = null;
        if(!"".equals(rids)){
            roleIds = rids.split(",");
        }
        return adminService.updateAdminRoles(adminId, roleIds);
    }

    @ApiOperation("更新当前用户信息")
    @PutMapping("/info")
    public RespData updateAdminInfo(@RequestBody Admin admin, Authentication authentication){
        if(adminService.updateById(admin)){
            //在SpringSecurity中重新设置Authentication对象
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(admin, "", authentication.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            return RespData.success("更新成功");
        }
        return RespData.error("更新失败");
    }

    @ApiOperation("更新用户密码")
    @PutMapping("/password")
    public RespData updatePassword(@RequestBody Map<String, Object> map){
        String oldPassword = (String) map.get("oldPassword");
        String newPassword = (String) map.get("newPassword");
        Integer adminId = (Integer) map.get("adminId");
        return adminService.updateAdminPassword(oldPassword, newPassword, adminId);

    }

    @ApiOperation("更新用户头像")
    @PostMapping("/userFace")
    public RespData updateAdminUserFace(MultipartFile file, Integer adminId, Authentication authentication){
        return adminService.updateAdminUserFace(file, adminId, authentication);
    }

}
