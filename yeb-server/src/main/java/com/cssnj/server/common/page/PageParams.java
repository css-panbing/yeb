package com.cssnj.server.common.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页参数
 *
 * @author panbing
 * @date 2022/3/26 16:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParams {

    /**
     * 当前页
     */
    private int currentPage;

    /**
     * 每页条数
     */
    private int pageSize;

}
