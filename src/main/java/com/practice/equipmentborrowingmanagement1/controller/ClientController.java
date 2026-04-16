package com.practice.equipmentborrowingmanagement1.controller;

import com.practice.equipmentborrowingmanagement1.model.dto.EquipmentFormRequest;
import com.practice.equipmentborrowingmanagement1.model.entity.Equipment;
import com.practice.equipmentborrowingmanagement1.service.EquipmentFormService;
import com.practice.equipmentborrowingmanagement1.service.EquipmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentFormService equipmentFormService;

    @GetMapping
    public String viewDashboard(Model model) {
        // Lấy danh sách thiết bị còn hàng (REQ-S01)
        model.addAttribute("equipments", equipmentService.getAvailableEquipments());

        if (!model.containsAttribute("equipmentFormRequest")) {
            model.addAttribute("equipmentFormRequest", new EquipmentFormRequest());
        }
        return "client/client";
    }

    @PostMapping("/borrow")
    public String submitBorrowRequest(
            @Valid @ModelAttribute("equipmentFormRequest") EquipmentFormRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        // VAL-02: Logic Thời gian
        if (request.getBorrowDate() != null && request.getBorrowDate().isBefore(LocalDate.now().plusDays(1))) {
            bindingResult.rejectValue("borrowDate", "error.borrowDate", "Ngày nhận phải ở tương lai");
        }
        if (request.getBorrowDate() != null && request.getReturnDate() != null
                && !request.getReturnDate().isAfter(request.getBorrowDate())) {
            bindingResult.rejectValue("returnDate", "error.returnDate", "Ngày trả phải sau ngày nhận");
        }

        // VAL-03: Bắt lỗi số lượng ngay từ Controller để View nhận được field error
        Equipment equipment = null;
        if (request.getEquipmentId() > 0) {
            equipment = equipmentService.getEquipmentById(request.getEquipmentId());
            if (request.getQuantity() == null || request.getQuantity() <= 0) {
                bindingResult.rejectValue("quantity", "error.quantity", "Số lượng phải là số nguyên dương lớn hơn 0");
            } else if (equipment != null && request.getQuantity() > equipment.getStock()) {
                bindingResult.rejectValue("quantity", "error.quantity",
                        "Vượt quá số lượng tồn kho (Chỉ còn " + equipment.getStock() + ")");
            }
        }

        // Ném lỗi về lại View nếu validation thất bại
        if (bindingResult.hasErrors()) {
            model.addAttribute("equipments", equipmentService.getAvailableEquipments());
            model.addAttribute("errorModalId", request.getEquipmentId());
            return "client/client";
        }

        try {
            equipmentFormService.processBorrow(request);

            // Thông báo thành công qua Flash Attributes
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký mượn thành công! Vui lòng chờ phê duyệt.");
        } catch (RuntimeException e) {
            // Bắt lỗi RuntimeException từ Service
            model.addAttribute("equipments", equipmentService.getAvailableEquipments());
            model.addAttribute("errorModalId", request.getEquipmentId());
            bindingResult.rejectValue("quantity", "error.quantity", e.getMessage());
            return "client/client";
        }

        return "redirect:/client";
    }
}