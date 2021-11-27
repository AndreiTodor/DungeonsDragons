package com.example.DungeonsDragons.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dd_equipment_category")
public class DDEquipmentCategory {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String index;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "dd_equipment_category_equipment",
            joinColumns = @JoinColumn(name = "equipment_category_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    private List<DDEquipmentItem> equipmentItems = new ArrayList<>();

    public DDEquipmentCategory() {};

    public DDEquipmentCategory(String index, String name) {
        this.index = index;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DDEquipmentItem> getEquipmentItems() {
        return equipmentItems;
    }

    public void setEquipmentItems(List<DDEquipmentItem> equipmentItems) {
        this.equipmentItems = equipmentItems;
    }

    public void addEquipmentItems(List<DDEquipmentItem> equipmentItems) {
        this.equipmentItems.addAll(equipmentItems);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DDEquipmentCategory that = (DDEquipmentCategory) o;
        return index.equals(that.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
