package com.cssnj.server.service.impl;

import com.cssnj.server.pojo.PoliticsStatus;
import com.cssnj.server.mapper.PoliticsStatusMapper;
import com.cssnj.server.service.IPoliticsStatusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 *  政治面貌实现类
 *
 * @author panbing
 * @since 2021-12-16
 */
@Service
public class PoliticsStatusServiceImpl extends ServiceImpl<PoliticsStatusMapper, PoliticsStatus> implements IPoliticsStatusService {

    @Autowired
    private PoliticsStatusMapper politicsStatusMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取所有的政治面貌
     * @return
     */
    @Override
    public List<PoliticsStatus> getPoliticsStatus() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        // 从Redis中查询数据
        List<PoliticsStatus> list = (List<PoliticsStatus>) valueOperations.get("PoliticsStatus");
        if(CollectionUtils.isEmpty(list)){
            list = politicsStatusMapper.selectList(null);
            valueOperations.set("PoliticsStatus", list);
        }
        return list;
    }
}
