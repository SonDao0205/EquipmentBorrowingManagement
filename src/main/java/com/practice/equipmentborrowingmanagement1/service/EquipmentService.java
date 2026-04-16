package com.practice.equipmentborrowingmanagement1.service;

import com.practice.equipmentborrowingmanagement1.model.entity.Equipment;
import com.practice.equipmentborrowingmanagement1.repository.EquipmentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    public String uploadFile(MultipartFile file, HttpServletRequest request) {
        if (file == null || file.isEmpty()) {
            return "default.png";
        }

        try {
            // Xác định thư mục lưu trữ
            String uploadPath = System.getProperty("user.dir") + "/uploads/images/";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // Tạo tên file duy nhất bằng Timestamp để không bị ghi đè
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // Đường dẫn đầy đủ của file
            Path path = Paths.get(uploadPath + fileName);

            // Copy file vào thư mục
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi lưu file: " + e.getMessage());
        }
    }
}
