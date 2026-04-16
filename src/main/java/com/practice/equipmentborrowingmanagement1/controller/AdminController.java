package com.practice.equipmentborrowingmanagement1.controller;

import com.practice.equipmentborrowingmanagement1.model.dto.EquipmentRequest;
import com.practice.equipmentborrowingmanagement1.model.entity.Equipment;
import com.practice.equipmentborrowingmanagement1.service.EquipmentFormService;
import com.practice.equipmentborrowingmanagement1.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentFormService equipmentFormService;

    @GetMapping("/inventory")
    public String viewInventory(Model model){
        model.addAttribute("items",equipmentService.getAllEquipment());
        return "admin/inventory";
    }

    @GetMapping("/dashboard")
    public String viewDashboard(Model model){
        model.addAttribute("requests",equipmentFormService.getEquipmentForms());
        return "admin/dashboard";
    }

    @GetMapping("/add-page")
    public String viewAddPage(Model model){
        model.addAttribute("equipment",new EquipmentRequest());
        return "admin/edit-form";
    }

    @PostMapping("/save")
    public String saveEquipment(EquipmentRequest equipmentRequest, Model model){
        return "redirect:/admin/inventory";
    }
}