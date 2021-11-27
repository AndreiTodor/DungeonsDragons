package com.example.DungeonsDragons.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dd_character")
public class DDCharacter {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message="Name is mandatory")
    @Column(nullable = false)
    private String name;

    @Min(value=0, message = "Age must be positive")
    @Column(nullable = false)
    private int age;

    @ManyToOne
    @JoinColumn(name="class_id")
    private DDClass ddClass;

    @ManyToOne
    @JoinColumn(name="race_id")
    private DDRace ddRace;

    @ManyToMany
    @JoinTable(
            name = "dd_character_equipment",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    private List<DDEquipment> equipments = new ArrayList<>();

    public DDCharacter() {}

    public DDCharacter(String name, int age) {
        this.name = name;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public DDClass getDdClass() {
        return ddClass;
    }

    public void setDdClass(DDClass ddClass) {
        this.ddClass = ddClass;
    }

    public List<DDEquipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<DDEquipment> equipments) {
        this.equipments = equipments;
    }

    public DDRace getDdRace() {
        return ddRace;
    }

    public void setDdRace(DDRace ddRace) {
        this.ddRace = ddRace;
    }
}
