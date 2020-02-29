package com.TheWorldFirst.ShareReading.service.ServiceImpl;

import com.TheWorldFirst.ShareReading.dao.UserDao;
import com.TheWorldFirst.ShareReading.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    //邮件发件人
    @Value("${mail.host.from}")
    private String fromName;

    @Override
    public boolean sendMail(String to, String subject, String validateCode) {
        //创建邮件正文
        StringBuffer emailContent = new StringBuffer();
        emailContent.append("尊敬的品论读书用户，您好！您的邮箱验证码是“"+validateCode+"”，此验证码24小时内有效。如非您本人操作，请忽略此邮件！");
        //简单邮件信息
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromName);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(emailContent.toString());
        try {
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
