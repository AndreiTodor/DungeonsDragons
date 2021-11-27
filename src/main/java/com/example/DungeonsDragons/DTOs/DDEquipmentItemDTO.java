package com.example.DungeonsDragons.DTOs;

import java.util.Objects;

public class DDEquipmentItemDTO {
    private Long id;
    private String index;

    public DDEquipmentItemDTO(Long id, String index) {
        this.id = id;
        this.index = index;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DDEquipmentItemDTO that = (DDEquipmentItemDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(index, that.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, index);
    }
}
