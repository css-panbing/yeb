package com.cssnj.server.common.utils;

import com.cssnj.server.pojo.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门树加工工具类
 * @author panbing
 * @date 2022/3/20 10:15
 */
public class DeptTreeNodeUtil {
    public static List<Department> getChildrenNode(Integer parentId, List<Department> treeDataList) {
        List<Department> result = new ArrayList<>();
        for (Department jsonTreeData : treeDataList) {
            if (jsonTreeData.getParentId() == null) continue;
            //这是一个子节点
            if (jsonTreeData.getParentId().equals(parentId)) {
                //递归获取子节点下的子节点
                jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId(), treeDataList));
                result.add(jsonTreeData);
            }
        }
        return result;
    }
}
