package com.cssnj.mail;

import com.cssnj.server.pojo.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * 邮件接受者
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

    @RabbitListener(queues = "mail.welcome")
    public void handler(Employee employee){
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        try {
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
            javaMailSender.send(message);
            logger.info("=====>邮件发送成功！");
        } catch (MessagingException e) {
            logger.error("=====>邮件发送失败：", e);
        }
    }


}
