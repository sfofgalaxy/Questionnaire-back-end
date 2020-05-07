package com.ziffer.questionnaire.service;

import com.ziffer.questionnaire.model.User;
import java.util.List;

public interface UserService {
    List<User> listAll();

    User selectByUsername(String username);

    int insertUser(User user);
}
