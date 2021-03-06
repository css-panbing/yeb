package com.cssnj.server.service.impl;

import com.cssnj.server.pojo.Oplog;
import com.cssnj.server.mapper.OplogMapper;
import com.cssnj.server.service.IOplogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author panbing
 * @since 2021-12-16
 */
@Service
public class OplogServiceImpl extends ServiceImpl<OplogMapper, Oplog> implements IOplogService {

}
