package com.example.DungeonsDragons.model;

import javax.persistence.*;

@Entity
@Table(name = "dd_category_equipment_option")
public class DDCategoryEquipmentOption{
    @Id
    @GeneratedValue
    private Long id;

    private int choose;

    @ManyToOne
    @JoinColumn(name="equipment_category_id")
    private DDEquipmentCategory equipmentCategory;

    public DDCategoryEquipmentOption() {};
    public DDCategoryEquipmentOption(int choose) {
        this.choose = choose;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getChoose() {
        return choose;
    }

    public void setChoose(int choose) {
        this.choose = choose;
    }

    public DDEquipmentCategory getEquipmentCategory() {
        return equipmentCategory;
    }

    public void setEquipmentCategory(DDEquipmentCategory equipmentCategory) {
        this.equipmentCategory = equipmentCategory;
    }
}
