package com.cssnj.server.service.impl;

import com.cssnj.server.pojo.Position;
import com.cssnj.server.mapper.PositionMapper;
import com.cssnj.server.service.IPositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取所有职位
     * @return
     */
    @Override
    public List<Position> getPositions() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        List<Position> list = (List<Position>) valueOperations.get("Positions");
        if(CollectionUtils.isEmpty(list)) {
            list = positionMapper.selectList(null);
            valueOperations.set("Positions", list);
        }
        return list;
    }
}
