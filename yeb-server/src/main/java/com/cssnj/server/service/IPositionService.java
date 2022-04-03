package com.cssnj.server.service;

import com.cssnj.server.pojo.Position;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 职位接口
 *
 * @author panbing
 * @since 2021-12-16
 */
public interface IPositionService extends IService<Position> {

    /**
     * 获取所有职位
     * @return
     */
    List<Position> getPositions();
}
