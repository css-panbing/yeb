package com.cssnj.server.service;

import com.cssnj.server.pojo.PoliticsStatus;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 政治面貌接口
 *
 * @author panbing
 * @since 2021-12-16
 */
public interface IPoliticsStatusService extends IService<PoliticsStatus> {

    /**
     * 获取所有的政治面貌
     * @return
     */
    List<PoliticsStatus> getPoliticsStatus();
}
