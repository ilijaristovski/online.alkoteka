package com.web.online.alkoteka.service;

import com.web.online.alkoteka.model.User;
import com.web.online.alkoteka.model.enumerations.Role;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService{

    User loadUserByUsername(String s) throws UsernameNotFoundException;

    User register(String username, String password, String repeatPassword, String name, String surname, Role userRole);
}
