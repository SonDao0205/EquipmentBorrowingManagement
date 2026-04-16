package com.practice.equipmentborrowingmanagement1.controller;

import com.practice.equipmentborrowingmanagement1.customException.EmailException;
import com.practice.equipmentborrowingmanagement1.customException.InvalidCredentialsException;
import com.practice.equipmentborrowingmanagement1.model.dto.UserRequest;
import com.practice.equipmentborrowingmanagement1.model.entity.User;
import com.practice.equipmentborrowingmanagement1.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ===================== REGISTER =====================

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserRequest());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("user") UserRequest request,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "auth/register";
        }

        try {
            userService.registerUser(request);
            return "redirect:/auth/login";

        } catch (EmailException e) {
            result.rejectValue("email", "error.user", e.getMessage());
            return "auth/register";
        }
    }

    // ===================== LOGIN =====================

    @GetMapping("/login")
    public String showLoginForm(Model model, HttpSession session) {

        if (session.getAttribute("userLogin") != null) {
            return "redirect:/home";
        }

        model.addAttribute("user", new UserRequest());
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute("user") UserRequest request,
            BindingResult result,
            Model model,
            HttpSession session
    ) {
        if (result.hasErrors()) {
            return "auth/login";
        }

        try {
            User user = userService.login(
                    request.getEmail(),
                    request.getPassword()
            );

            session.setAttribute("userLogin", user.getId());
            session.setAttribute("role", user.getRole());

            return switch (user.getRole()) { // điều hướng
                case ADMIN -> "redirect:/admin";
                case STUDENT -> "redirect:/student";
            };

        } catch (InvalidCredentialsException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/login";
        }
    }

    // ===================== LOGOUT =====================

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}