package com.practice.equipmentborrowingmanagement1.model.dto;

import java.time.LocalDate;

public class EquipmentFormRequest {
    private String fullName;
    private String studentId;
    private String email;
    private Integer quantity;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String reason;
    private int equipmentId;

    public EquipmentFormRequest() {
    }

    public EquipmentFormRequest(String fullName, String studentId, String email, Integer quantity, LocalDate borrowDate, LocalDate returnDate, String reason, int equipmentId) {
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

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }
}
