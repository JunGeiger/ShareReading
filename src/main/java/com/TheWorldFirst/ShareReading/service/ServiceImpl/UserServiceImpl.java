package com.TheWorldFirst.ShareReading.service.ServiceImpl;

import com.TheWorldFirst.ShareReading.dao.UserDao;
import com.TheWorldFirst.ShareReading.service.MailService;
import com.TheWorldFirst.ShareReading.service.UserService;
import com.TheWorldFirst.ShareReading.util.RandomCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailService mailService;

    @Override
    public HashMap<String, Object> register(HashMap<String, Object> user) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            HashMap<String, Object> emailCode = userDao.getValidateCode((String) user.get("email"));
            HashMap<String, Object> existUserByName  = userDao.getUserByName((String) user.get("username"));
            HashMap<String, Object> existUserByEmail  = userDao.getUserByEmail((String) user.get("email"));
            if (existUserByName != null) {
                result.put("success", false);
                result.put("message", "用户名已存在！");
            } else if (existUserByEmail != null){
                result.put("success", false);
                result.put("message", "邮箱已被注册！");
            } else {
                if (emailCode != null) {
                    if(emailCode.get("validateCode").equals(user.get("validateCode"))) {
                        userDao.register(user);
                        result.put("success", true);
                        result.put("message", "注册成功！");
                    } else {
                        result.put("success", false);
                        result.put("message", "验证码错误！");
                    }
                } else {
                    result.put("success", false);
                    result.put("message", "请重新获取验证码！");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "注册失败，请联系管理员！");
        }
        return result;
    }

    @Override
    public HashMap<String, Object> updatePassword(HashMap<String, Object> user) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            HashMap<String, Object> emailCode = userDao.getValidateCode((String) user.get("email"));
            HashMap<String, Object> existUserByEmail  = userDao.getUserByEmail((String) user.get("email"));
            if (existUserByEmail == null){
                result.put("success", false);
                result.put("message", "该邮箱未注册！");
                return result;
            } else if (emailCode == null) {
                result.put("success", false);
                result.put("message", "请重新获取验证码！");
                return result;
            } else if(emailCode.get("validateCode").equals(user.get("validateCode"))) {
                user.put("id", existUserByEmail.get("id"));
                userDao.updatePassword(user);
                result.put("success", true);
                result.put("message", "密码修改成功！");
                return result;
            } else {
                result.put("success", false);
                result.put("message", "验证码错误！");
                return result;
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "修改失败，请联系管理员！");
        }
        return result;
    }

    @Override
    public HashMap<String, Object> existUserByEmail(String eMail) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            HashMap<String, Object> existUserByEmail  = userDao.getUserByEmail(eMail);
            if(existUserByEmail != null) {
                result.put("success", true);
                result.put("message", "查询成功！");
            } else {
                result.put("success", false);
                result.put("message", "该邮箱还未注册！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "查询失败，请联系管理员！");
        }
        return result;
    }

    @Override
    public HashMap<String, Object> getValidateCode(String eMail) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            //如果邮件未过期，则提醒用户去邮箱查看或等一等
            HashMap<String, Object> emailCode = userDao.getValidateCode(eMail);
            //是否已存在未过期验证码
            if(emailCode != null) {
                result.put("success", true);
                result.put("message", "验证码还未过期，请去邮箱内查看（若收件箱内没有邮件，请查看一下垃圾箱是否被拦截）！");
            } else {
                //获取随机生成的验证码
                String validateCode = RandomCode.getRandomCode(4);
                Boolean isSuccess = mailService.sendMail(eMail, "品论读书(ShareReading)的邮箱验证码", validateCode);
                if(isSuccess) {
                    userDao.setValidateCodeInvalid(eMail);
                    userDao.saveValidateCode(eMail, validateCode);
                    result.put("success", true);
                    result.put("message", "验证码发送成功！");
                } else {
                    result.put("success", false);
                    result.put("message", "验证码发送失败，请检查邮箱是否正确！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "验证码保存失败，请联系管理员处理！");
        }
        return result;
    }
}
