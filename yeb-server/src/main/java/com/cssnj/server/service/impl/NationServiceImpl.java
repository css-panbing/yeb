package com.cssnj.server.service.impl;

import com.cssnj.server.pojo.Nation;
import com.cssnj.server.mapper.NationMapper;
import com.cssnj.server.service.INationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 *  民族接口实现类
 *
 * @author panbing
 * @since 2021-12-16
 */
@Service
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements INationService {

    @Autowired
    private NationMapper nationMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取所有民族
     * @return
     */
    @Override
    public List<Nation> getNations() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        List<Nation> list = (List<Nation>) valueOperations.get("Nations");
        if(CollectionUtils.isEmpty(list)){
            list = nationMapper.selectList(null);
            valueOperations.set("Nations", list);
        }
        return list;
    }
}
