package com.cssnj.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cssnj.server.common.pojo.SelectTree;
import com.cssnj.server.common.response.RespData;
import com.cssnj.server.common.utils.DeptTreeNodeUtil;
import com.cssnj.server.common.utils.SelectTreeNodeUtil;
import com.cssnj.server.pojo.Department;
import com.cssnj.server.mapper.DepartmentMapper;
import com.cssnj.server.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门管理 实现类
 *
 * @author panbing
 * @since 2021-12-16
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 获取所有部门列表
     * @param parentId
     * @return
     */
    @Override
    public List<Department> getAllDepartments(Integer parentId) {
        List<Department> list = departmentMapper.getAllDepartments(parentId);
        //递归遍历出子节点
        List<Department> result = DeptTreeNodeUtil.getChildrenNode(parentId, list);
        return result;
    }

    /**
     * 根据查询条件查询部门信息
     * @param department
     * @return
     */
    @Override
    public List<Department> getDeptByParams(Department department) {
        List<Department> list = departmentMapper.selectList(new QueryWrapper<Department>().like("name", department.getName()));
        return list;
    }

    /**
     * 查询部门下拉树
     * @return
     */
    @Override
    public List<SelectTree> getDeptSelectTree() {
        List<SelectTree> list = departmentMapper.getDeptSelectTree();
        //递归遍历出子节点
        List<SelectTree> result = SelectTreeNodeUtil.getChildrenNode(-1, list);
        return result;
    }

    /**
     * 添加部门
     * @param department
     * @return
     */
    @Override
    @Transactional
    public RespData addDepartment(Department department) {
        if(department.getParentId() == null){
            return RespData.error("请确认该部门的上级部门");
        }
        department.setEnabled(true);
        department.setCreateDate(LocalDateTime.now());
        //1、添加子部门
        int insert = departmentMapper.insert(department);
        Department parentDept = departmentMapper.selectById(department.getParentId());//获取到父部门
        parentDept.setIsParent(true);
        //2、更新父部门isParent属性
        int update = departmentMapper.updateById(parentDept);
        if(insert == 1 && update == 1){
            return RespData.success("添加部门成功");
        }
        return RespData.error("添加部门失败");
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @Override
    @Transactional
    public RespData deleteDepartment(Integer id) {
        //1、判断是否存在子部门
        Integer count = departmentMapper.selectCount(new QueryWrapper<Department>().eq("parentId", id));
        if(count > 0){
            return RespData.error("该部门存在子部门，无法删除");
        }
        Department delDept = departmentMapper.selectById(id);
        //2、删除该部门
        int delete = departmentMapper.deleteById(id);
        //3、判断是否需要更新其父部门的isParent属性（查询其父部门是否还存在子部门）
        count = departmentMapper.selectCount(new QueryWrapper<Department>().eq("parentId", delDept.getParentId()));
        if(count == 0){//不存在子部门，需要更新isParent属性
            //查询其父部门
            Department delParentDept = departmentMapper.selectOne(new QueryWrapper<Department>().eq("id", delDept.getParentId()));
            //更新父部门的isParent属性
            delParentDept.setIsParent(false);
            int update = departmentMapper.updateById(delParentDept);
            if(delete == 1 && update == 1){
                return RespData.success("删除成功");
            }
        }
        if(delete == 1){
            return RespData.success("删除成功");
        }
        return RespData.error("删除失败");
    }

}
