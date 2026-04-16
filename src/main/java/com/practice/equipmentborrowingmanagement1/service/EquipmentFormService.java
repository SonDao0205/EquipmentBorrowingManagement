package com.practice.equipmentborrowingmanagement1.service;

import com.practice.equipmentborrowingmanagement1.model.dto.EquipmentFormRequest;
import com.practice.equipmentborrowingmanagement1.model.dto.EquipmentRequest;
import com.practice.equipmentborrowingmanagement1.model.entity.Equipment;
import com.practice.equipmentborrowingmanagement1.model.entity.EquipmentForm;
import com.practice.equipmentborrowingmanagement1.repository.EquipmentFormRepository;
import com.practice.equipmentborrowingmanagement1.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipmentFormService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentFormRepository equipmentFormRepository;

    public void processBorrow(EquipmentFormRequest form) {

        Equipment equipment = equipmentRepository.findById(form.getEquipmentId());

        if (equipment == null) {
            throw new RuntimeException("Thiết bị không tồn tại");
        }

        if (form.getQuantity() <= 0) {
            throw new RuntimeException("Số lượng phải lớn hơn 0");
        }

        if (form.getQuantity() > equipment.getStock()) {
            throw new RuntimeException("Số lượng vượt quá tồn kho");
        }

        equipment.setStock(
                equipment.getStock() - form.getQuantity()
        );

        equipmentRepository.update(equipment);

        EquipmentForm request = new EquipmentForm();

        request.setFullName(form.getFullName());
        request.setStudentId(form.getStudentId());
        request.setEmail(form.getEmail());
        request.setQuantity(form.getQuantity());
        request.setBorrowDate(form.getBorrowDate());
        request.setReturnDate(form.getReturnDate());
        request.setReason(form.getReason());
        request.setEquipment(equipment);

        equipmentFormRepository.save(request);
    }

    public void deleteBorrow(int id) {
        EquipmentForm request = equipmentFormRepository.findById(id);
        if (request == null) {
            throw new RuntimeException("Không tìm thấy phiếu cần xóa");
        }

        Equipment equipment = request.getEquipment();

        if (equipment == null){
            throw new RuntimeException("Thiết bị không tồn tại");
        }
        equipment.setStock(equipment.getStock() + request.getQuantity());
        equipmentRepository.update(equipment);
    }
}
