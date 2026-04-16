package com.practice.equipmentborrowingmanagement1.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EquipmentRequest {
    @NotBlank(message = "Tên thiết bị không được bỏ trống")
    private String name;

    @NotBlank(message = "Loại thiết bị không được bỏ trống")
    private String type;

    @NotBlank(message = "Hình ảnh không được bỏ trống")
    private String image;

    @NotNull(message = "Số lượng không được bỏ trống")
    @Min(value = 0, message = "Số lượng không được nhỏ hơn 0")
    private Integer stock;

    public EquipmentRequest() {
    }

    public EquipmentRequest(String name, String type, String image, Integer stock) {
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
