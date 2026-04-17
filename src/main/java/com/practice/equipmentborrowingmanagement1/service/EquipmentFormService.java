package com.practice.equipmentborrowingmanagement1.service;

import com.practice.equipmentborrowingmanagement1.model.dto.EquipmentFormRequest;
import com.practice.equipmentborrowingmanagement1.model.dto.EquipmentRequest;
import com.practice.equipmentborrowingmanagement1.model.entity.Equipment;
import com.practice.equipmentborrowingmanagement1.model.entity.EquipmentForm;
import com.practice.equipmentborrowingmanagement1.repository.EquipmentFormRepository;
import com.practice.equipmentborrowingmanagement1.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentFormService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentFormRepository equipmentFormRepository;

    public List<EquipmentForm> getEquipmentForms() {
        return equipmentFormRepository.findAll();
    }

    public void processBorrow(EquipmentFormRequest form) {
        Equipment equipment = equipmentRepository.findById(form.getEquipmentId());

        if (equipment == null) {
            throw new RuntimeException("Thiết bị không tồn tại");
        }

        // Vẫn cần kiểm tra sơ bộ để tránh user đăng ký số lượng tào lao
        if (form.getQuantity() <= 0) {
            throw new RuntimeException("Số lượng phải lớn hơn 0");
        }

        EquipmentForm request = new EquipmentForm();
        request.setFullName(form.getFullName());
        request.setStudentId(form.getStudentId());
        request.setEmail(form.getEmail());
        request.setQuantity(form.getQuantity());
        request.setBorrowDate(form.getBorrowDate());
        request.setReturnDate(form.getReturnDate());
        request.setReason(form.getReason());
        request.setEquipment(equipment);
        request.setStatus(false); // Mặc định là chưa duyệt

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

    public void updateStatus(int id){
        EquipmentForm form = equipmentFormRepository.findById(id);

        if (form == null) {
            throw new RuntimeException("Không tìm thấy đơn hàng");
        }

        // Nếu đơn đã duyệt rồi thì không làm gì cả (tránh trừ kho 2 lần)
        if (form.isStatus()) {
            return;
        }

        Equipment equipment = form.getEquipment();

        // Kiểm tra kho tại thời điểm duyệt
        if (equipment.getStock() < form.getQuantity()) {
            throw new RuntimeException("Không đủ hàng trong kho để duyệt đơn này!");
        }

        // Thực hiện trừ kho
        equipment.setStock(equipment.getStock() - form.getQuantity());
        equipmentRepository.update(equipment);

        // Cập nhật trạng thái đơn thành Đã Duyệt
        form.setStatus(true);
        equipmentFormRepository.update(form);
    }

    public void returnEquipment(int id) {
        EquipmentForm form = equipmentFormRepository.findById(id);
        if (form == null) {
            throw new RuntimeException("Không tìm thấy phiếu mượn");
        }

        // Chỉ cho phép trả nếu đơn đã được duyệt (đã trừ kho trước đó)
        if (!form.isStatus()) {
            throw new RuntimeException("Đơn này chưa được duyệt, không thể thực hiện trả");
        }

        Equipment equipment = form.getEquipment();
        if (equipment != null) {
            // Cộng lại số lượng vào kho
            equipment.setStock(equipment.getStock() + form.getQuantity());
            equipmentRepository.update(equipment);
        }

        // Sau khi trả xong, bạn có thể xóa đơn để dọn dẹp danh sách
        equipmentFormRepository.delete(id);
    }
}
