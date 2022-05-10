package com.web.online.alkoteka.service;


import com.web.online.alkoteka.model.User;


public interface AuthService {
    User login(String username, String password);
}
