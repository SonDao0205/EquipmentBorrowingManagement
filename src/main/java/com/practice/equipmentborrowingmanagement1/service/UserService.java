package com.practice.equipmentborrowingmanagement1.service;

import com.practice.equipmentborrowingmanagement1.model.entity.User;

import java.util.Optional;

public interface UserService {
    void registerUser(User user);
    User login(String email,String password);
    Optional<User> findUserByEmail(String email);
}
