package com.cssnj.server.service;

import com.cssnj.server.common.page.PageParams;
import com.cssnj.server.common.response.RespData;
import com.cssnj.server.common.response.RespPageData;
import com.cssnj.server.pojo.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工接口
 *
 * @author panbing
 * @since 2021-12-16
 */
public interface IEmployeeService extends IService<Employee> {

    /**
     * 分页查询员工信息
     * @param pageParams 分页参数
     * @param employee 员工信息
     * @param beginDateScope 入职日期区间
     * @return
     */
    RespPageData getEmployeeByPage(PageParams pageParams, Employee employee, LocalDate[] beginDateScope);

    /**
     * 自动生成工号
     * @return
     */
    RespData generateWorkId();

    /**
     * 添加员工信息
     * @param employee
     * @return
     */
    RespData addEmployee(Employee employee);

    /**
     * 更新员工信息
     * @param employee
     * @return
     */
    RespData updateEmployee(Employee employee);

    /**
     * 查询所有员工
     * @return
     */
    List<Employee> getAllEmployee();
}
