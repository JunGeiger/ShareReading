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
}
