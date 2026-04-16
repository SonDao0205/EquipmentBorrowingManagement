package com.practice.equipmentborrowingmanagement1.service;

import com.practice.equipmentborrowingmanagement1.model.entity.User;
import com.practice.equipmentborrowingmanagement1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean registerUser(User user) {

        if (user == null){
            return false;
        }

        return false;
    }

    @Override
    public boolean login(String username, String password, String confirmPassword) {
        return false;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return null;
    }
}
