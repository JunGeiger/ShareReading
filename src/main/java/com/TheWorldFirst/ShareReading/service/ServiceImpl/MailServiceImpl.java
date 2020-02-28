package com.TheWorldFirst.ShareReading.service.ServiceImpl;

import com.TheWorldFirst.ShareReading.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    //邮件发件人
    @Value("${mail.host.hostname}")
    private String hostname;

    @Override
    public boolean sendMail(String to, String subject, String validateCode, String username) {
        //创建邮件正文
        StringBuffer emailContent = new StringBuffer();
        emailContent.append("<!DOCTYPE html>");
        emailContent.append("<html lang='zh-cn' xmlns:th='http://www.thymeleaf.org'>");
        emailContent.append("<head>");
        emailContent.append("<meta charset='UTF-8'>");
        emailContent.append("<title>品论读书(ShareReading)</title>");
        emailContent.append("</head>");
        emailContent.append("<body style='text-align: center;'>");
        emailContent.append("尊敬的"+username+"，您好！您的邮箱验证码是"+validateCode+"，此验证码24小时内有效。如非您本人操作，请忽略此邮件！");
        emailContent.append("</body>");
        emailContent.append("</html>");
        //简单邮件信息
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(hostname);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(emailContent.toString());
        try {
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
