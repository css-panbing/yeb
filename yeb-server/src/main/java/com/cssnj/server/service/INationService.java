package com.cssnj.server.service;

import com.cssnj.server.pojo.Nation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 民族接口
 *
 * @author panbing
 * @since 2021-12-16
 */
public interface INationService extends IService<Nation> {

    /**
     * 获取所有民族
     * @return
     */
    List<Nation> getNations();
}
