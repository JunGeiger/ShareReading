package com.TheWorldFirst.ShareReading.service;

public interface MailService {
    /**
     * 发送邮件
     * @param to 收件箱
     * @param subject 邮件主题
     * @param validateCode 验证码
     */
    boolean sendMail(String to, String subject, String validateCode);
}
