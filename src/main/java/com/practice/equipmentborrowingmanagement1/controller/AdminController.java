package com.practice.equipmentborrowingmanagement1.controller;

import com.practice.equipmentborrowingmanagement1.model.dto.EquipmentRequest;
import com.practice.equipmentborrowingmanagement1.model.entity.Equipment;
import com.practice.equipmentborrowingmanagement1.service.EquipmentFormService;
import com.practice.equipmentborrowingmanagement1.service.EquipmentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentFormService equipmentFormService;

    @GetMapping("/inventory")
    public String viewInventory(Model model) {
        model.addAttribute("items", equipmentService.getAllEquipment());
        return "admin/inventory";
    }

    @GetMapping("/dashboard")
    public String viewDashboard(Model model) {
        model.addAttribute("requests", equipmentFormService.getEquipmentForms());
        return "admin/dashboard";
    }

    @GetMapping("/add-page")
    public String viewAddPage(Model model) {
        model.addAttribute("equipment", new EquipmentRequest());
        return "admin/edit-form";
    }

    @PostMapping("/save")
    public String saveEquipment(@Valid @ModelAttribute("equipment") EquipmentRequest equipmentRequest,
                                BindingResult result,
                                @RequestParam("fileImage") MultipartFile file) {
        if (result.hasErrors()) {
            return "admin/edit-form";
        }
        Equipment equipment = new Equipment();
        equipment.setStatus(true);
        equipment.setName(equipmentRequest.getName());
        equipment.setType(equipmentRequest.getType());
        equipment.setStock(equipmentRequest.getStock());
        equipment.setImage(equipmentService.uploadFile(file));
        equipmentService.addEquipment(equipment);
        return "redirect:/admin/inventory";
    }

    @GetMapping("/delete/{id}")
    public String deleteEquipment(@PathVariable("id") Integer id) {
        equipmentService.deleteEquipment(id);
        return "redirect:/admin/inventory";
    }

    @GetMapping("/approve/{id}")
    public String approveEquipmentForm(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            equipmentFormService.updateStatus(id);
            redirectAttributes.addFlashAttribute("success", "Đã duyệt đơn và trừ kho thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/return/{id}")
    public String returnEquipment(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            equipmentFormService.returnEquipment(id);
            redirectAttributes.addFlashAttribute("success", "Xác nhận trả thiết bị thành công! Số lượng đã được hoàn vào kho.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }
}