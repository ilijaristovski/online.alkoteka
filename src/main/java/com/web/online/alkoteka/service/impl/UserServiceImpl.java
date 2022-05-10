package com.web.online.alkoteka.service.impl;

import com.web.online.alkoteka.model.User;
import com.web.online.alkoteka.model.enumerations.Role;
import com.web.online.alkoteka.model.exceptions.InvalidUsernameOrPasswordException;
import com.web.online.alkoteka.model.exceptions.PasswordsDoNotMatchException;
import com.web.online.alkoteka.model.exceptions.UsernameAlreadyExistsException;
import com.web.online.alkoteka.repository.UserRepository;
import com.web.online.alkoteka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //private final PasswordEncoder passwordEncoder;

//    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }

    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElseThrow(()->new UsernameNotFoundException(s));
    }


    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, Role userRole) {
        if (username==null || username.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if (!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();
        if(this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);
        String encodedPassword = password;//passwordEncoder.encode(password);
        User user = new User(username,encodedPassword, encodedPassword, name, surname,  userRole);
        return userRepository.save(user);
    }
}
