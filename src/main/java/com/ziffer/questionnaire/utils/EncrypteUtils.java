package com.ziffer.questionnaire.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class EncrypteUtils {
    @Value("${spring.authorize.salt}")
    private String salt;

    public String getMD5Code(String username, String password) {
        String base = username + "/" + salt + ":"+password;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
