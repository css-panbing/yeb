package com.cssnj.server.service.impl;

import com.cssnj.server.pojo.Department;
import com.cssnj.server.mapper.DepartmentMapper;
import com.cssnj.server.service.IDepartmentService;
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
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
