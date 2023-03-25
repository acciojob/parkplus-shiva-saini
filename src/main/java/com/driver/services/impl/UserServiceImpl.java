package com.driver.services.impl;

import com.driver.Entity.User;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository4;
    @Override
    public void deleteUser(Integer userId) {
       userRepository4.deleteById(userId);
    }

    @Override
    public User updatePassword(Integer userId, String password) {
        User user;
        try{
            user = userRepository4.findById(userId).get();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage()+"Invalid user Id");
        }
        user.setPassword(password);
        userRepository4.save(user);
        return  user;
    }

    @Override
    public void register(String name, String phoneNumber, String password) {
            User user = new User(name,phoneNumber,password);
            userRepository4.save(user);
    }
}
