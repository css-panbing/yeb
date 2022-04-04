package com.cssnj.server.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cssnj.server.common.pojo.MailConstants;
import com.cssnj.server.pojo.Employee;
import com.cssnj.server.pojo.MailLog;
import com.cssnj.server.service.IEmployeeService;
import com.cssnj.server.service.IMailLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 邮件发送定时任务
 *
 * @author panbing
 * @date 2022/4/4 16:00
 */
@Component
public class MailTask {

    @Autowired
    private IMailLogService mailLogService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 邮件发送定时任务
     * 每分钟秒执行一次
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void mailTask(){
        List<MailLog> list = mailLogService.list(new QueryWrapper<MailLog>().eq("status", 0).lt("tryTime", LocalDateTime.now()));
        list.forEach(mailLog -> {
            if(mailLog.getCount() >= MailConstants.MAX_TRY_COUNT){//如果重试次数超过最大重试次数（3次），更新状态为失败2
                mailLogService.update(new UpdateWrapper<MailLog>().set("status", 2).eq("msgId", mailLog.getMsgId()));
            }else {
                mailLogService.update(new UpdateWrapper<MailLog>().set("count", mailLog.getCount()+1)
                        .set("tryTime", LocalDateTime.now().plusSeconds(MailConstants.MSG_TIMEOUT))
                        .set("updateTime", LocalDateTime.now()).eq("msgId", mailLog.getMsgId()));
            }
            Employee employee = employeeService.getById(mailLog.getEid());
            //重新发送邮件
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY_NAME,
                    employee, new CorrelationData(mailLog.getMsgId()));

        });
    }

}
