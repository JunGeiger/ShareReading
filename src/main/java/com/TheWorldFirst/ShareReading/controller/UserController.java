package com.TheWorldFirst.ShareReading.controller;

import com.TheWorldFirst.ShareReading.service.MailService;
import com.TheWorldFirst.ShareReading.service.UserService;
import com.TheWorldFirst.ShareReading.util.RandomCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @PostMapping("/register")
    public HashMap<String, Object> register(@RequestBody HashMap<String, Object> user) {
        return userService.register(user);
    }

    @GetMapping("/getValidateCode")
    public HashMap<String, Object> getValidateCode(@RequestParam("eMail") String eMail, @RequestParam("username") String username) {
        String validateCode = RandomCode.getRandomCode(4);
        Boolean isSuccess = mailService.sendMail(eMail, "品论读书(ShareReading)的邮箱验证码", validateCode, username);
        return userService.getValidateCode(isSuccess, eMail, validateCode, username);
    }
}
