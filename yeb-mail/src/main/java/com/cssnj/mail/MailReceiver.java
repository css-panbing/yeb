package com.cssnj.mail;

import com.cssnj.server.common.pojo.MailConstants;
import com.cssnj.server.pojo.Employee;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

/**
 * 消息接收者
 * @author panbing
 * @date 2022/4/3 22:22
 */
@Component
public class MailReceiver {

    private static final Logger logger = LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(queues = MailConstants.MAIL_QUEUE_NAME)
    public void handler(Message message, Channel channel){
        Employee employee = (Employee) message.getPayload();
        MessageHeaders headers = message.getHeaders();
        //消息序号
        long tag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);
        String msgId = (String) headers.get("spring_returned_message_correlation");
        HashOperations hashOperations = redisTemplate.opsForHash();
        try {
            if(hashOperations.entries("mail_log").containsKey(msgId)){
                logger.info("消息已经被消费========>{}", msgId);
                //手动确认消息（tag:消息序号，multiple:是否确认多条）
                channel.basicAck(tag, false);
                return;
            }
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            // 发件人
            messageHelper.setFrom(mailProperties.getUsername());
            // 收件人
            messageHelper.setTo(employee.getEmail());
            // 主题
            messageHelper.setSubject("入职欢迎邮件");
            // 发送日期
            messageHelper.setSentDate(new Date());
            // 邮件内容
            Context context = new Context();
            context.setVariable("name", employee.getName());
            context.setVariable("departmentName", employee.getDepartment().getName());
            context.setVariable("posName", employee.getPosition().getName());
            context.setVariable("joblevelName", employee.getJoblevel().getName());
            String mail = templateEngine.process("mail", context);
            messageHelper.setText(mail, true);
            //发送邮件
            javaMailSender.send(mimeMessage);
            logger.info("=====>邮件发送成功！");
            //将消息ID存入Redis
            hashOperations.put("mail_log", msgId, "OK");
            //手动确认消息
            channel.basicAck(tag, false);
        } catch (MessagingException | IOException e) {
            try {
                /**
                 * 手动确认消息
                 * tag:消息序号
                 * multiple:是否确认多条
                 * requeue:是否退回到队列
                 */
                channel.basicNack(tag, false, true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
                logger.error("邮件发送失败=====>{}", e);
            }
            logger.error("邮件发送失败=====>{}", e);
        }
    }


}
