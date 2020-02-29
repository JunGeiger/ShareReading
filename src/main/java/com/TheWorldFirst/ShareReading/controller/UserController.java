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

    @PostMapping("/register")
    public HashMap<String, Object> register(@RequestBody HashMap<String, Object> user) {
        return userService.register(user);
    }

    @GetMapping("/getValidateCode")
    public HashMap<String, Object> getValidateCode(@RequestParam("eMail") String eMail) {

        return userService.getValidateCode(eMail);
    }
}
