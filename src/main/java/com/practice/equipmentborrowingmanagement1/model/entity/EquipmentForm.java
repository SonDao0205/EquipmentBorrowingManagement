package com.practice.equipmentborrowingmanagement1.model.entity;

import java.time.LocalDate;

public class EquipmentForm {
    private int id;
    private String fullName;
    private String studentId;
    private String email;
    private int quantity;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String reason;
    private boolean status;
    private Equipment equipment;

    public EquipmentForm() {
    }

    public EquipmentForm(int id, String fullName, String studentId, String email, int quantity, LocalDate borrowDate, LocalDate returnDate, String reason, boolean status, Equipment equipment) {
        this.id = id;
        this.fullName = fullName;
        this.studentId = studentId;
        this.email = email;
        this.quantity = quantity;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.reason = reason;
        this.status = status;
        this.equipment = equipment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
