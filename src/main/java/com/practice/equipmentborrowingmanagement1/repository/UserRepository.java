package com.practice.equipmentborrowingmanagement1.repository;

import com.practice.equipmentborrowingmanagement1.model.entity.Role;
import com.practice.equipmentborrowingmanagement1.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private static final List<User> users = new ArrayList<>();
    private static int currentId = 1;

    static {
        User admin = new User();
        admin.setId(currentId++);
        admin.setFullName("Admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("admin123");
        admin.setRole(Role.ADMIN);

        User student = new User();
        student.setId(currentId++);
        student.setFullName("Student");
        student.setEmail("student@gmail.com");
        student.setPassword("stu123");
        student.setRole(Role.STUDENT);

        users.add(admin);
        users.add(student);
    }

    // Lấy tất cả
    public List<User> findAll() {
        return users;
    }

    // Tìm theo id
    public User findById(int id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Lưu user
    public void save(User user) {
        user.setId(currentId++);
        users.add(user);
    }

    // Cập nhật
    public void update(User updated) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == updated.getId()) {
                users.set(i, updated);
                break;
            }
        }
    }

    // Xóa
    public void delete(int id) {
        users.removeIf(u -> u.getId() == id);
    }

    // Tìm theo email
    public User findByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    // Kiểm tra email tồn tại
    public boolean existsByEmail(String email) {
        return users.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    // Login
    public User findByEmailAndPassword(String email, String password) {
        return users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email)
                        && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    // Tìm theo role
    public List<User> findByRole(Role role) {
        return users.stream()
                .filter(u -> u.getRole() == role)
                .toList();
    }

}