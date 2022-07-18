package com.nerotomato.email.service;

public interface IMailSendService {

     /**
      * 简单邮件发送
      * */
     void sendSimpleMail(String to, String cc, String subject, String content);
}
