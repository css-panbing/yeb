package com.cssnj.server.service.impl;

import com.cssnj.server.pojo.Position;
import com.cssnj.server.mapper.PositionMapper;
import com.cssnj.server.service.IPositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 职位接口实现类
 *
 * @author panbing
 * @since 2021-12-16
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements IPositionService {

    @Autowired
    private PositionMapper positionMapper;


}
