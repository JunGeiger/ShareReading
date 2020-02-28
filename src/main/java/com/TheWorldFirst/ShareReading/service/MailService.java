package com.TheWorldFirst.ShareReading.service;

public interface MailService {
    /**
     * 发送邮件
     * @param to 邮件收件人
     * @param subject 邮件主题
     * @param verifyCode 邮件验证码
     */
    boolean sendMail(String to, String subject, String verifyCode, String username);
}
