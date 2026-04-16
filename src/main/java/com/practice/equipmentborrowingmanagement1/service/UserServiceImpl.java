package com.practice.equipmentborrowingmanagement1.service;

import com.practice.equipmentborrowingmanagement1.customException.EmailException;
import com.practice.equipmentborrowingmanagement1.customException.InvalidCredentialsException;
import com.practice.equipmentborrowingmanagement1.model.dto.UserRequest;
import com.practice.equipmentborrowingmanagement1.model.entity.Role;
import com.practice.equipmentborrowingmanagement1.model.entity.User;
import com.practice.equipmentborrowingmanagement1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerUser(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailException("Email đã tồn tại");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.STUDENT);

        userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new InvalidCredentialsException("Email hoặc mật khẩu không đúng");
        }

        if (!user.getPassword().equals(password)) {
            throw new InvalidCredentialsException("Email hoặc mật khẩu không đúng");
        }

        return user;
    }
}