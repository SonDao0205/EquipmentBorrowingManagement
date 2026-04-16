package com.practice.equipmentborrowingmanagement1.controller;

import com.practice.equipmentborrowingmanagement1.model.dto.EquipmentFormRequest;
import com.practice.equipmentborrowingmanagement1.model.dto.EquipmentRequest;
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

@Controller
@RequestMapping("/equipment")
public class EquipmentFormController {

    @Autowired
    private EquipmentFormService equipmentFormService;

    @Autowired
    private EquipmentService equipmentService;

    @GetMapping("/borrow/{id}")
    public String showBorrowForm(@PathVariable("id") int id, Model model) {

        Equipment equipment = equipmentService.getEquipmentById(id);

        if (equipment == null){
            return "redirect:/equipment";
        }

        EquipmentFormRequest form = new EquipmentFormRequest();
        form.setEquipmentId(id);

        model.addAttribute("equipment", equipment);
        model.addAttribute("equipmentFormRequest", form);

        return "client/equipment-form";
    }

    @PostMapping("/submit")
    public String submitForm(@Valid @ModelAttribute("equipmentFormRequest") EquipmentFormRequest equipmentFormRequest,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        Equipment equipment = equipmentService.getEquipmentById(equipmentFormRequest.getEquipmentId());

        if (equipment == null){
            redirectAttributes.addFlashAttribute("error", "Thiết bị không tồn tại");
            return "redirect:/equipment";
        }

        if (bindingResult.hasErrors()){
            model.addAttribute("equipment", equipment);
            return "client/equipment-form";
        }

        try {
            equipmentFormService.processBorrow(equipmentFormRequest);
        }catch (RuntimeException e){
            bindingResult.rejectValue("quantity", "error.quantity", e.getMessage());
            model.addAttribute("equipment", equipment);
            return "client/equipment-form";
        }

        redirectAttributes.addFlashAttribute("success", "Mượn thiết bị thành công");

        return "redirect:/equipment";
    }

    @PostMapping("delete")
    public String delete(@Valid @RequestParam("equipId") int id, RedirectAttributes redirectAttributes) {
        try{
            equipmentFormService.deleteBorrow(id);
            redirectAttributes.addFlashAttribute("success", "Xóa phiếu mượn thành công");
        }catch (RuntimeException e){
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy phiếu mượn cần xóa");
            return "redirect:/equipment/form";
        }
        return "redirect:/equipment";
    }
}
