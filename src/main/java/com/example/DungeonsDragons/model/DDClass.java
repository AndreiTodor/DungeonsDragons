package com.example.DungeonsDragons.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dd_class")
public class DDClass {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String index;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "dd_class_starting_equipment",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "starting_equipment_id")
    )
    private List<DDEquipment> startingEquipments;

    @OneToMany
    @JoinTable(
            name = "dd_class_starter_equipment_option",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "starter_equipment_option_id")
    )
    private List<DDStarterEquipmentOption> starterEquipmentOptions;

    public DDClass() {};
    public DDClass(String index, String name) {
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

    public List<DDEquipment> getStartingEquipments() {
        return startingEquipments;
    }

    public void setStartingEquipments(List<DDEquipment> startingEquipments) {
        this.startingEquipments = startingEquipments;
    }

    public List<DDStarterEquipmentOption> getStarterEquipmentOptions() {
        return starterEquipmentOptions;
    }

    public void setStarterEquipmentOptions(List<DDStarterEquipmentOption> starterEquipmentOptions) {
        this.starterEquipmentOptions = starterEquipmentOptions;
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
        DDClass ddClass = (DDClass) o;
        return Objects.equals(index, ddClass.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }
}
