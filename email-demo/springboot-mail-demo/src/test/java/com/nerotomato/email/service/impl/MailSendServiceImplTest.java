package com.nerotomato.email.service.impl;

import com.nerotomato.email.SpringBootMailDemo;
import com.nerotomato.email.service.IMailSendService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootMailDemo.class)
class MailSendServiceImplTest {
    @Autowired
    private IMailSendService iMailSendService;
    @Test
    public void testSimpleMail(){
        iMailSendService.sendSimpleMail("2362849729@qq.com","909210754@qq.com","测试邮件","测试邮件发送");
    }
}