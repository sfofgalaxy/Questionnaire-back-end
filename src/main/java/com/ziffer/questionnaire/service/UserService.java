package com.ziffer.questionnaire.service;

import com.ziffer.questionnaire.model.User;
import java.util.List;

public interface UserService {
    List<User> listAll();
    User getByUsername(String username);
    int register(User user);
    boolean updatePassword(User user);
}
