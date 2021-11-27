package com.example.DungeonsDragons.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dd_equipment_item")
public class DDEquipmentItem {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String index;

    @Column(nullable = false)
    private String name;

    public DDEquipmentItem() {};
    public DDEquipmentItem(String index, String name) {
        this.index = index;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        DDEquipmentItem that = (DDEquipmentItem) o;
        return index.equals(that.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
