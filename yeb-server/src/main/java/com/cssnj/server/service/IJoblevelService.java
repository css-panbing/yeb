package com.cssnj.server.service;

import com.cssnj.server.pojo.Joblevel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 职称接口
 *
 * @author panbing
 * @since 2021-12-16
 */
public interface IJoblevelService extends IService<Joblevel> {

    /**
     * 获取所有职称
     * @return
     */
    List<Joblevel> getJoblevels();
}
