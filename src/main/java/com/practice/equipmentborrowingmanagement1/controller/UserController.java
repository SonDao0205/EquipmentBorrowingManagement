package com.practice.equipmentborrowingmanagement1.controller;

import com.practice.equipmentborrowingmanagement1.customException.EmailException;
import com.practice.equipmentborrowingmanagement1.customException.InvalidCredentialsException;
import com.practice.equipmentborrowingmanagement1.model.dto.UserRequest;
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
@RequestMapping("/auth")
public class    UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserRequest());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("user") UserRequest user,
            BindingResult result
    ){
        if (result.hasErrors()){
            return "auth/register";
        }

        try {
            userService.registerUser(user);
            return "redirect:/auth/login"; // chuyển sang trang login
        } catch (EmailException e) {
            result.rejectValue("email", "error.user", e.getMessage());
            return "auth/register";
        }
    }

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        User userLogin = (User) session.getAttribute("userLogin");
        if (userLogin != null) {
            return (userLogin.getRole() == Role.ADMIN) ? "redirect:/admin/inventory" : "redirect:/student";
        }

        model.addAttribute("user", new User());
        return "auth/login"; // Sửa đường dẫn trả về
    }

    @PostMapping("/login")
    public String doLogin(@ModelAttribute("user") User user, Model model, HttpSession session) {
        try {
            User u = userService.login(user.getEmail(), user.getPassword());
            session.setAttribute("userLogin", u);

            if (u.getRole() == Role.ADMIN){
                return "redirect:/admin/inventory";
            }
            if (u.getRole() == Role.STUDENT) {
                return "redirect:/student";
            }

            return "redirect:/home";
        } catch (InvalidCredentialsException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/login"; // Sửa đường dẫn trả về
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login"; // Sửa đường dẫn redirect
    }
}