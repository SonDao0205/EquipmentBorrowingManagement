package com.practice.equipmentborrowingmanagement1.model.dto;

import com.practice.equipmentborrowingmanagement1.custom_validator.ReturnDateValid;
import com.practice.equipmentborrowingmanagement1.custom_validator.StudentIdValid;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@ReturnDateValid
public class EquipmentFormRequest {
    @NotBlank(message = "Họ và tên không được để trống")
    private String fullName;

    @NotBlank(message = "Mã sinh viên không được để trống")
    @StudentIdValid
    private String studentId;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotNull(message = "Số lượng mượn không được bỏ trống")
    @Min(value = 1, message = "Số lượng mượn ít nhất phải là 1")
    private Integer quantity;

    @NotNull(message = "Ngày nhận không được bỏ trống!")
    @Future(message = "Ngày dự kiến nhận phải là ngày trong tương lai")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate borrowDate;

    @NotNull(message = "Ngày trả không được bỏ trống!")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate returnDate;

    @NotBlank(message = "Lý do mượn không được để trống")
    private String reason;

    @NotNull(message = "Chưa chọn thiết bị")
    private Integer equipmentId;

    public EquipmentFormRequest() {
    }

    public EquipmentFormRequest(String fullName, String studentId, String email, Integer quantity, LocalDate borrowDate, LocalDate returnDate, String reason, Integer equipmentId) {
        this.fullName = fullName;
        this.studentId = studentId;
        this.email = email;
        this.quantity = quantity;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.reason = reason;
        this.equipmentId = equipmentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }
}
