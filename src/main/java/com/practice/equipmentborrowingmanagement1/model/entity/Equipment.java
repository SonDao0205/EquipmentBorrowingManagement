package com.practice.equipmentborrowingmanagement1.model.entity;

public class Equipment {
    private int id;
    private String name;
    private String type;
    private String image;
    private int stock;
    private boolean status;

    public Equipment() {
    }

    public Equipment(int id, String name, String type, String image, int stock, boolean status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.image = image;
        this.stock = stock;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
