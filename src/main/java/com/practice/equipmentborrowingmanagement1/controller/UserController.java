package com.practice.equipmentborrowingmanagement1.controller;

import com.practice.equipmentborrowingmanagement1.customException.EmailException;
import com.practice.equipmentborrowingmanagement1.customException.InvalidCredentialsException;
import com.practice.equipmentborrowingmanagement1.model.entity.Role;
import com.practice.equipmentborrowingmanagement1.model.entity.User;
import com.practice.equipmentborrowingmanagement1.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Đăng ký tài khoản
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("user") User user,
            BindingResult result
    ){
        if (result.hasErrors()) return "register";

        try {
            userService.registerUser(user);
            return "redirect:/user/login";
        } catch (EmailException e) {
            result.rejectValue("email", "error.user", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {

        User userLogin = (User) session.getAttribute("userLogin");

        // kiểu tra user có tồn tại không
        if (userLogin != null) {
            if (userLogin.getRole() == Role.ADMIN) {
                return "redirect:/admin";
            } else {
                return "redirect:/student";
            }
        }

        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(
            @ModelAttribute("user") User user,
            Model model,
            HttpSession session
    ) {
        try {
            User u = userService.login(user.getEmail(), user.getPassword());

            // lưu session
            session.setAttribute("userLogin", u);
            //  phân quyền
            if (u.getRole() == Role.ADMIN) {
                return "redirect:/admin";
            } else if (u.getRole() == Role.STUDENT) {
                return "redirect:/student";
            }

            return "redirect:/home";

        } catch (InvalidCredentialsException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    // Đăng xuất khỏi hệ thống
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }
}