package com.cssnj.server.common.utils;

import com.cssnj.server.pojo.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单树加工工具类
 * @author panbing
 * @date 2022/3/21 14:09
 */
public class MenuTreeNodeUtil {
    public static List<Menu> getChildrenNode(Integer parentId, List<Menu> treeDataList) {
        List<Menu> result = new ArrayList<>();
        for (Menu jsonTreeData : treeDataList) {
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
