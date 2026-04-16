package com.practice.equipmentborrowingmanagement1.service;

import com.practice.equipmentborrowingmanagement1.model.entity.User;

import java.util.Optional;

public interface UserService {
    boolean registerUser(User user);
    boolean login(String username,String password,String confirmPassword);
    Optional<User> findUserByEmail(String email);
}
