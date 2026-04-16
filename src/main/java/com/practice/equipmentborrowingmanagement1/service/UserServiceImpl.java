package com.practice.equipmentborrowingmanagement1.service;

import com.practice.equipmentborrowingmanagement1.customException.EmailException;
import com.practice.equipmentborrowingmanagement1.customException.InvalidCredentialsException;
import com.practice.equipmentborrowingmanagement1.model.dto.UserRequest;
import com.practice.equipmentborrowingmanagement1.model.entity.Role;
import com.practice.equipmentborrowingmanagement1.model.entity.User;
import com.practice.equipmentborrowingmanagement1.repository.UserRepository;
import org.springframework.stereotype.Service;
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // thực hiện ddangw kí
    @Override
    public void registerUser(UserRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("Request không được null");
        }

        if (request.getEmail() == null || request.getPassword() == null) {
            throw new IllegalArgumentException("Email và password không được để trống");
        }

        // check email tồn tại
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

    // đăng nhập
    @Override
    public User login(String email, String password) {

        if (email == null || password == null) {
            throw new InvalidCredentialsException("Email hoặc mật khẩu không đúng");
        }

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new InvalidCredentialsException("Email hoặc mật khẩu không đúng");
        }

        if (!password.equals(user.getPassword())) {
            throw new InvalidCredentialsException("Email hoặc mật khẩu không đúng");
        }

        return user;
    }
}