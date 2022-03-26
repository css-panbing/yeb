package com.cssnj.server.controller;

import com.cssnj.server.common.pojo.SelectTree;
import com.cssnj.server.common.response.RespData;
import com.cssnj.server.pojo.Department;
import com.cssnj.server.service.IDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 部门管理
 *
 * @author panbing
 * @since 2021-12-16
 */
@RestController
@Api(tags = "部门管理")
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments(-1);
    }

    @ApiOperation(value = "通过查询查询条件获取部门")
    @GetMapping("/list")
    public List<Department> getDeptByParams(Department department){
        return departmentService.getDeptByParams(department);
    }

    @ApiOperation(value = "获取部门下拉树")
    @GetMapping("/selectTree")
    public List<SelectTree> getDeptSelectTree(){
        return departmentService.getDeptSelectTree();
    }

    @ApiOperation(value = "添加部门")
    @PostMapping("/")
    public RespData addDepartment(@RequestBody Department department){
        return departmentService.addDepartment(department);
    }

    @ApiOperation(value = "删除部门")
    @DeleteMapping("/{id}")
    public RespData deleteDepartment(@PathVariable("id") Integer id){
        return departmentService.deleteDepartment(id);
    }

}
