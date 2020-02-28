package com.TheWorldFirst.ShareReading.service.ServiceImpl;

import com.TheWorldFirst.ShareReading.dao.UserDao;
import com.TheWorldFirst.ShareReading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public HashMap<String, Object> register(HashMap<String, Object> user) {
        return null;
    }

    @Override
    public HashMap<String, Object> getValidateCode(Boolean isSuccess, String eMail, String validateCode, String username) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            if(isSuccess) {
                result.put("success", true);
                result.put("message", "验证码发送失败，请联系管理员处理！");
            } else {
                userDao.setValidateCodeInvalid(eMail);
                userDao.saveValidateCode(eMail, validateCode);
                result.put("success", false);
                result.put("message", "验证码发送成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "验证码保存失败，请联系管理员处理！");
        }
        return result;
    }
}
