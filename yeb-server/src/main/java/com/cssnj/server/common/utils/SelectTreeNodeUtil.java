package com.cssnj.server.common.utils;

import com.cssnj.server.common.pojo.SelectTree;
import java.util.ArrayList;
import java.util.List;

/**
 * SelectTreeNodeUtil下拉树节点工具类
 * @author panbing
 * @date 2022/3/19 21:53
 */
public class SelectTreeNodeUtil {

    /**
     * 加工子节点
     * @param pid
     * @param treeDataList
     * @return
     */
    public static List<SelectTree> getChildrenNode(Integer pid, List<SelectTree> treeDataList) {
        List<SelectTree> result = new ArrayList<>();
        for (SelectTree jsonTreeData : treeDataList) {
            if (jsonTreeData.getPid() == null) continue;
            //这是一个子节点
            if (jsonTreeData.getPid().equals(pid)) {
                //递归获取子节点下的子节点
                jsonTreeData.setChildren(getChildrenNode(jsonTreeData.getId(), treeDataList));
                result.add(jsonTreeData);
            }
        }
        return result;
    }

}
