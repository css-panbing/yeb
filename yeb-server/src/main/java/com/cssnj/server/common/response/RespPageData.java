package com.cssnj.server.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页公共返回对象
 * @author panbing
 * @date 2022/3/26 16:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespPageData {

    /**
     * 总条数
     */
    private long total;

    /**
     * 数据
     */
    private List<?> data;

}
