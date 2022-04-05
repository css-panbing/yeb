package com.cssnj.server.controller;


import com.cssnj.server.common.response.RespData;
import com.cssnj.server.pojo.Salary;
import com.cssnj.server.service.ISalaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 工资账套Controller
 *
 * @author panbing
 * @since 2021-12-16
 */
@RestController
@Api(tags = "工资账套管理")
@RequestMapping("/salary/sob")
public class SalaryController {

    @Autowired
    private ISalaryService salaryService;

    @ApiOperation("获取所有工资账套")
    @GetMapping("/")
    public List<Salary> getAllSalary(){
        return salaryService.list();
    }

    @ApiOperation("添加工资账套信息")
    @PostMapping("/")
    public RespData addSalary(@RequestBody Salary salary){
        salary.setCreateDate(LocalDateTime.now());
        if(salaryService.save(salary)){
            return RespData.success("添加成功");
        }
        return RespData.error("添加失败");
    }

    @ApiOperation("更新工资账套信息")
    @PutMapping("/")
    public RespData updateSalary(@RequestBody Salary salary){
        if(salaryService.save(salary)){
            return RespData.success("更新成功");
        }
        return RespData.error("更新失败");
    }

    @ApiOperation("删除工资账套信息")
    @DeleteMapping("/{id}")
    public RespData deleteSalary(@PathVariable Integer id){
        if(salaryService.removeById(id)){
            return RespData.success("删除成功");
        }
        return RespData.error("删除失败");
    }
}
