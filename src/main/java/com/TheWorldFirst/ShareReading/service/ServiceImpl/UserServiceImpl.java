package com.TheWorldFirst.ShareReading.service.ServiceImpl;

import com.TheWorldFirst.ShareReading.dao.UserDao;
import com.TheWorldFirst.ShareReading.service.MailService;
import com.TheWorldFirst.ShareReading.service.UserService;
import com.TheWorldFirst.ShareReading.util.RandomCode;
import com.TheWorldFirst.ShareReading.util.SessionRandomCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailService mailService;

    private Date date = new Date();

    @Override
    @Transactional
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
    @Transactional
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
    @Transactional
    public HashMap<String, Object> login(HashMap<String, Object> user) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            HashMap<String, Object> userInfo = null;
            HashMap<String, Object> existUserByName  = userDao.getUserByNameBinary((String) user.get("username"));
            HashMap<String, Object> existUserByEmail  = userDao.getUserByEmail((String) user.get("username"));
            if(existUserByName == null && existUserByEmail == null) {
                result.put("success", false);
                result.put("message", "用户名或邮箱填写错误！");
            } else {
                if(existUserByName != null) {
                    userInfo = existUserByName;
                } else if (existUserByEmail != null) {
                    userInfo = existUserByEmail;
                }
                if (userInfo != null) {
                    if (userInfo.get("user_password").equals(user.get("password"))) {
                        String session = SessionRandomCode.getSessionRandomCode(32);
                        HashMap<String, Object> userInfoRole = userDao.getUserInfo(String.valueOf(userInfo.get("id")));
                        userDao.deleteLoginSession(String.valueOf(userInfoRole.get("id")));
                        userDao.saveLoginSession((Long)userInfoRole.get("id"),session );
                        userInfoRole.put("session", session);
                        userInfoRole.put("time", date.getTime());
                        result.put("user", userInfoRole);
                        result.put("success", true);
                        result.put("message", "登录成功！");
                    } else {
                        result.put("success", false);
                        result.put("message", "密码错误！");
                    }
                } else {
                    result.put("success", false);
                    result.put("message", "登录失败！");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "登录失败，请联系管理员！");
        }
        return result;
    }

    @Override
    @Transactional
    public HashMap<String, Object> sessionLogin(HashMap<String, Object> user) {
        HashMap<String, Object> result = new HashMap<>();
        try {
//
            HashMap<String, Object> sessionInfo = userDao.getLoginSession((String) user.get("id"), (String) user.get("session"));
            userDao.deleteLoginSession((String)user.get("id"));
            if (sessionInfo != null) {
                String session = SessionRandomCode.getSessionRandomCode(32);
                HashMap<String, Object> userInfo = userDao.getUserInfo((String) user.get("id"));
                userDao.saveLoginSession((Long)userInfo.get("id"), session);
                userInfo.put("session", session);
                userInfo.put("time", date.getTime());
                result.put("user", userInfo);
                result.put("success", true);
                result.put("message", "登录成功！");
            } else {
                result.put("success", false);
                result.put("message", "登录失败！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "登录失败，请联系管理员！");
        }
        return result;
    }

    @Override
    @Transactional
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
