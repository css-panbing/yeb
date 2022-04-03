package com.cssnj.server.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cssnj.server.pojo.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工 Mapper 接口
 *
 * @author panbing
 * @since 2021-12-16
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 分页查询员工信息
     * @param page 分页信息
     * @param employee 员工信息
     * @param beginDateScope 入职日期区间
     * @return
     */
    IPage<Employee> getEmployeeByPage(Page<Employee> page, @Param("employee") Employee employee, @Param("beginDateScope") LocalDate[] beginDateScope);

    /**
     * 查询所有员工
     * @return
     */
    List<Employee> getAllEmployee();
}
