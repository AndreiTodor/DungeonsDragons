package com.example.DungeonsDragons.DTOs;

import java.util.Objects;

public class DDEquipmentDTO {
    private Long id;
    private int quantity;
    private DDEquipmentItemDTO item;

    public DDEquipmentDTO(Long id, int quantity, DDEquipmentItemDTO item) {
        this.id = id;
        this.quantity = quantity;
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DDEquipmentItemDTO getItem() {
        return item;
    }

    public void setItem(DDEquipmentItemDTO item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DDEquipmentDTO that = (DDEquipmentDTO) o;
        return quantity == that.quantity && Objects.equals(id, that.id) && Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, item);
    }
}
