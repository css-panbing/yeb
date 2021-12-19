package com.cssnj.server.service.impl;

import com.cssnj.server.pojo.Employee;
import com.cssnj.server.mapper.EmployeeMapper;
import com.cssnj.server.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author panbing
 * @since 2021-12-16
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
