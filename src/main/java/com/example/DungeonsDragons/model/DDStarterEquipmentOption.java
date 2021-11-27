package com.example.DungeonsDragons.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dd_starter_equipment_option")
public class DDStarterEquipmentOption {
    @Id
    @GeneratedValue
    private Long id;

    private int choose;

    @OneToMany
    @JoinTable(
            name = "dd_starter_equipment_option_bundle",
            joinColumns = @JoinColumn(name = "starter_equipment_option_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_bundle_id")
    )
    private List<DDEquipmentBundle> bundles = new ArrayList<>();

    public DDStarterEquipmentOption() {};
    public DDStarterEquipmentOption(int choose) {
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

    public List<DDEquipmentBundle> getBundles() {
        return bundles;
    }

    public void setBundles(List<DDEquipmentBundle> bundles) {
        this.bundles = bundles;
    }
}
