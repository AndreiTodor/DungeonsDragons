package com.example.DungeonsDragons.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dd_equipment_bundle")
public class DDEquipmentBundle {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "dd_equipment_bundle_equipment",
            joinColumns = @JoinColumn(name = "equipment_bundle_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    private List<DDEquipment> equipments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "dd_equipment_bundle_category",
            joinColumns = @JoinColumn(name = "equipment_bundle_id"),
            inverseJoinColumns = @JoinColumn(name = "category_equipment_option_id")
    )
    private List<DDCategoryEquipmentOption> categoryEquipmentOptions = new ArrayList<>();

    public DDEquipmentBundle() {};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DDEquipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<DDEquipment> equipments) {
        this.equipments = equipments;
    }

    public List<DDCategoryEquipmentOption> getCategoryEquipmentOptions() {
        return categoryEquipmentOptions;
    }

    public void setCategoryEquipmentOptions(List<DDCategoryEquipmentOption> categoryEquipmentOptions) {
        this.categoryEquipmentOptions = categoryEquipmentOptions;
    }
}
