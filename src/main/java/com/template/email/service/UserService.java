package com.template.email.service;
import com.template.email.model.User;

public interface UserService {

    User saveUser (User user);
    Boolean verifyToken(String token);
}
