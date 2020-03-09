package com.TheWorldFirst.ShareReading.service;

import java.util.HashMap;

public interface UserService {
    /**
     * 注册方法
     * @param user
     * @return
     */
    HashMap<String, Object> register(HashMap<String, Object> user);

    /**
     * 发送邮箱验证码方法
     * @param eMail
     * @return
     */
    HashMap<String, Object> getValidateCode(String eMail);

    /**
     * 修改密码
     * @param user
     * @return
     */
    HashMap<String, Object> updatePassword(HashMap<String, Object> user);

    /**
     * 根据邮箱获取用户
     * @param eMail
     * @return
     */
    HashMap<String, Object> existUserByEmail(String eMail);

    /**
     * 用户登录
     * @param user
     * @return
     */
    HashMap<String, Object> login(HashMap<String, Object> user);

    /**
     * 用户使用session登录
     * @param user
     * @return
     */
    HashMap<String, Object> sessionLogin(HashMap<String, Object> user);
}
