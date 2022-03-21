package com.cssnj.server.mapper;

import com.cssnj.server.common.pojo.SelectTree;
import com.cssnj.server.pojo.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author panbing
 * @since 2021-12-16
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 获取所有部门列表
     * @return
     */
    List<Department> getAllDepartments(Integer parentId);

    /**
     * 查询部门下拉树
     * @return
     */
    List<SelectTree> getDeptSelectTree();
}
