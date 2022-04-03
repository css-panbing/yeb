package com.cssnj.server.service.impl;

import com.cssnj.server.pojo.Joblevel;
import com.cssnj.server.mapper.JoblevelMapper;
import com.cssnj.server.service.IJoblevelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 *  职称接口实现类
 *
 * @author panbing
 * @since 2021-12-16
 */
@Service
public class JoblevelServiceImpl extends ServiceImpl<JoblevelMapper, Joblevel> implements IJoblevelService {

    @Autowired
    private JoblevelMapper joblevelMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取所有职称
     * @return
     */
    @Override
    public List<Joblevel> getJoblevels() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        List<Joblevel> list = (List<Joblevel>) valueOperations.get("Joblevels");
        if(CollectionUtils.isEmpty(list)){
            list = joblevelMapper.selectList(null);
            valueOperations.set("Joblevels", list);
        }
        return list;
    }
}
