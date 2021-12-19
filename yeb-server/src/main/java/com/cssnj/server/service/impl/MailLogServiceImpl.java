package com.cssnj.server.service.impl;

import com.cssnj.server.pojo.MailLog;
import com.cssnj.server.mapper.MailLogMapper;
import com.cssnj.server.service.IMailLogService;
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
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements IMailLogService {

}
