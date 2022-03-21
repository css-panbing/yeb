package com.cssnj.server.service;

import com.cssnj.server.common.pojo.SelectTree;
import com.cssnj.server.common.response.ResponseData;
import com.cssnj.server.pojo.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 部门管理
 *
 * @author panbing
 * @since 2021-12-16
 */
public interface IDepartmentService extends IService<Department> {

    /**
     * 查询所有部门
     * @return
     */
    List<Department> getAllDepartments(Integer parentId);

    /**
     * 添加部门
     * @param department
     * @return
     */
    ResponseData addDepartment(Department department);

    /**
     * 删除部门
     * @param id
     * @return
     */
    ResponseData deleteDepartment(Integer id);

    /**
     * 通过查询条件查询部门信息
     * @param department
     * @return
     */
    List<Department> getDeptByParams(Department department);

    /**
     * 获取部门下拉树
     * @return
     */
    List<SelectTree> getDeptSelectTree();
}
