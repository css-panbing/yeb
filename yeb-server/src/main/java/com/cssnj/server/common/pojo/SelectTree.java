package com.cssnj.server.common.pojo;

import com.cssnj.server.common.utils.SelectTreeNodeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 公共返回树
 * @author panbing
 * @date 2022/3/19 21:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectTree {

    private Integer id;
    public Integer pid;
    private String value;
    private String name;
    private String label;
    private String title;
    private String url;
    private String open;
    private String clickOpen;
    private String openType;
    private Integer isParent;
    private Object data;
    private boolean disable = false;
    private boolean spread = false;
    private boolean checked = false;

    private List<SelectTree> children;

}
