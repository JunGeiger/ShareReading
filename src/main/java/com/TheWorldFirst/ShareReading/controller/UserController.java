package com.TheWorldFirst.ShareReading.controller;

import com.TheWorldFirst.ShareReading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public HashMap<String, Object> login(@RequestBody HashMap<String, Object> user) {
        return userService.login(user);
    }

    /**
     * 登录，根据session
     * @param user
     * @return
     */
    @PostMapping("/sessionLogin")
    public HashMap<String, Object> sessionLogin(@RequestBody HashMap<String, Object> user) {
        return userService.sessionLogin(user);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public HashMap<String, Object> register(@RequestBody HashMap<String, Object> user) {
        return userService.register(user);
    }

    /**
     * 检测用户是否存在。根据Email
     * @param eMail
     * @return
     */
    @GetMapping("/existUserByEmail")
    public HashMap<String, Object> existUserByEmail(@RequestParam("eMail") String eMail) {
        return userService.existUserByEmail(eMail);
    }

    /**
     * 修改密码
     * @param user
     * @return
     */
    @PostMapping("/updatePassword")
    public HashMap<String, Object> updatePassword(@RequestBody HashMap<String, Object> user) {
        return userService.updatePassword(user);
    }

    /**
     * 获取验证码
     * @param eMail
     * @return
     */
    @GetMapping("/getValidateCode")
    public HashMap<String, Object> getValidateCode(@RequestParam("eMail") String eMail) {
        return userService.getValidateCode(eMail);
    }
}
