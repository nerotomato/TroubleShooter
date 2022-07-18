package com.nerotomato.email.service.impl;

import com.nerotomato.email.service.IMailSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 邮件发送Service
 *
 * @author nerotomato
 * @date 2022/07/18
 * */
@Slf4j
@Service
public class MailSendServiceImpl implements IMailSendService {

    /**
     * 发件人
     * */
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendSimpleMail(String to, String cc, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setCc(cc);
        message.setSubject(subject);
        message.setText(content);
        try {
            javaMailSender.send(message);
            log.info("Send email successfully!");
        } catch (Exception e) {
            log.error("Failed sending email!"+e);
        }
    }
}
