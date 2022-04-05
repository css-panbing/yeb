package com.cssnj.server.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cssnj.server.common.page.PageParams;
import com.cssnj.server.common.response.RespData;
import com.cssnj.server.common.response.RespPageData;
import com.cssnj.server.pojo.Employee;
import com.cssnj.server.pojo.Salary;
import com.cssnj.server.service.IEmployeeService;
import com.cssnj.server.service.ISalaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 员工账套设置Controller
 * @author panbing
 * @date 2022/4/4 22:12
 */
@RestController
@Api(tags = "员工账套")
@RequestMapping("/salary/sobcfg")
public class SalarySobController {

    @Autowired
    private ISalaryService salaryService;
    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation("获取所有工资账套")
    @GetMapping("/salaries")
    public List<Salary> getAllSalary(){
        return salaryService.list();
    }

    @ApiOperation("获取所有员工账套")
    @GetMapping("/")
    public RespPageData getEmployeeWithSalary(PageParams pageParams){
        return employeeService.getEmployeeWithSalary(pageParams);
    }

    @ApiOperation("更新员工账套信息")
    @PutMapping("/")
    public RespData updateEmployeeSalary(Integer eid, Integer sid){
        if(employeeService.update(new UpdateWrapper<Employee>().set("salaryId", sid).eq("id", eid))){
            return RespData.success("更新成功");
        }
        return RespData.error("更新失败");
    }

}
