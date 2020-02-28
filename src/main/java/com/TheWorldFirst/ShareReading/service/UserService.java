package com.TheWorldFirst.ShareReading.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface UserService {
    HashMap<String, Object> register(HashMap<String, Object> user);

    HashMap<String, Object> getValidateCode(Boolean isSuccess, String eMail, String validateCode, String username);
}
