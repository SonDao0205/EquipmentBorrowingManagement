package com.practice.equipmentborrowingmanagement1.service;

import com.practice.equipmentborrowingmanagement1.model.entity.Equipment;
import com.practice.equipmentborrowingmanagement1.repository.EquipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    // Lấy tất cả
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    // Lấy thiết bị theo id
    public Equipment getEquipmentById(int id) {
        return equipmentRepository.findById(id);
    }

    // Chỉ lấy thiết bị còn hàng và đang active
    public List<Equipment> getAvailableEquipments() {
        return equipmentRepository.findAll().stream()
                .filter(e -> e.getStock() > 0 && e.isStatus())
                .collect(Collectors.toList());
    }

    // Kiểm tra tồn tại
    public boolean exists(int id) {
        return equipmentRepository.existsById(id);
    }

    // Kiểm tra hợp lệ
    public boolean isAvailable(int id, int quantity) {
        if (quantity <= 0) return false;
        Equipment equip = equipmentRepository.findById(id);
        return equip != null && equip.isStatus() && equip.getStock() >= quantity;
    }

    // Kiểm tra số lượng tồn kho
    public int getAvailableQuantity(int id) {
        return equipmentRepository.getAvailableQuantity(id);
    }

    // Mượn thiết bị
    public boolean borrowEquipment(int id, int quantity) {
        if (!exists(id) || !isAvailable(id, quantity)) {
            return false;
        }

        equipmentRepository.decreaseQuantity(id, quantity);
        return true;
    }

    // Trả thiết bị
    public void returnEquipment(int id, int quantity) {
        if (quantity > 0 && exists(id)) {
            equipmentRepository.increaseQuantity(id, quantity);
        }
    }

    // Thêm thiết bị
    public void addEquipment(Equipment equipment) {
        equipmentRepository.save(equipment);
    }

    // Cập nhật thiết bị
    public boolean updateEquipment(Equipment equipment) {
        if (!exists(equipment.getId())) {
            return false;
        }

        equipmentRepository.update(equipment);
        return true;
    }

    // Xóa thiết bị
    public boolean deleteEquipment(int id) {
        if (!exists(id)) {
            return false;
        }

        equipmentRepository.delete(id);
        return true;
    }
}
