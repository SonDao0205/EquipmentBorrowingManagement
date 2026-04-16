package com.practice.equipmentborrowingmanagement1.model.dto;

public class EquipmentRequest {
    private String name;
    private String type;
    private String image;
    private int stock;

    public EquipmentRequest() {
    }

    public EquipmentRequest(String name, String type, String image, int stock) {
        this.name = name;
        this.type = type;
        this.image = image;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
