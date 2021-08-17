package com.nerotomato.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.io.File;

/**
 * @Created by nerotomato on 2021/8/13.
 */
public class CommonsEmailDemo {
    //private static final String SMTP_SERVER = "smtp-mail.outlook.com"; // smtp.live.com
    //private static final String SMTP_SERVER = "smtp.live.com";
    private static final String SMTP_SERVER = "smtp.qq.com";
    private static final int SMTP_PORT = 25;
    private static final String SMTP_SSL_PORT = "465";
    //private static final String SMTP_SSL_PORT = "587"; //微软 outlook ssl端口
    private static final String FROM_MAIL = "**@**.com";
    private static final String FROM_MAIL_PASSWORD = "******"; //如果是QQ邮箱，需要设置为账号里面SMTP协议的授权码
    private static final String[] recipientAddress = {"**@**.com", "**@**.com"};
    private static final String filePath = "/home/nerotomato/图片/壁纸/test.jpeg";

    public static void main(String[] args) throws EmailException {
        sendEmail();
        System.out.println("Send mail to user");
    }

    private static void sendEmail() throws EmailException {
        //发件邮箱认证
        DefaultAuthenticator authenticator = new DefaultAuthenticator(FROM_MAIL, FROM_MAIL_PASSWORD);
        HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.setCharset("UTF-8");
        //设置邮箱服务器地址
        htmlEmail.setHostName(SMTP_SERVER);
        //设置邮箱服务器端口
        htmlEmail.setSmtpPort(SMTP_PORT);
        //设置邮箱服务器加密端口
        //htmlEmail.setSSLOnConnect(true);
        //htmlEmail.setSslSmtpPort(SMTP_SSL_PORT);
        //设置authenticator
        htmlEmail.setAuthenticator(authenticator);
        //设置发件人地址
        htmlEmail.setFrom(FROM_MAIL);
        //设置收件人地址,可以数组形式设置多个地址
        htmlEmail.addTo(recipientAddress);
        htmlEmail.setSubject("测试邮件");
        htmlEmail.setHtmlMsg(" <html><body><b>this is test email message!</body></html> ");
        // 添加附件
        addAttachment(htmlEmail, filePath);
        htmlEmail.send();
    }

    public static void addAttachment(HtmlEmail htmlEmail, String path) throws EmailException {
        // Create the attachment
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath(path);
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("test file");
        String fileName = new File(path).getName();
        attachment.setName(fileName);
        // add the attachment
        htmlEmail.attach(attachment);
    }
}
