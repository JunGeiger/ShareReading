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

    @PostMapping("/login")
    public HashMap<String, Object> login(@RequestBody HashMap<String, Object> user) {
        return userService.login(user);
    }

    @PostMapping("/sessionLogin")
    public HashMap<String, Object> sessionLogin(@RequestBody HashMap<String, Object> user) {
        return userService.sessionLogin(user);
    }

    @PostMapping("/register")
    public HashMap<String, Object> register(@RequestBody HashMap<String, Object> user) {
        return userService.register(user);
    }

    @GetMapping("/existUserByEmail")
    public HashMap<String, Object> existUserByEmail(@RequestParam("eMail") String eMail) {
        return userService.existUserByEmail(eMail);
    }

    @PostMapping("/updatePassword")
    public HashMap<String, Object> updatePassword(@RequestBody HashMap<String, Object> user) {
        return userService.updatePassword(user);
    }

    @GetMapping("/getValidateCode")
    public HashMap<String, Object> getValidateCode(@RequestParam("eMail") String eMail) {
        return userService.getValidateCode(eMail);
    }
}
