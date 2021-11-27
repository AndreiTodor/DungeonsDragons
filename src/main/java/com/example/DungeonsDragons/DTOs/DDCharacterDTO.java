package com.example.DungeonsDragons.DTOs;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DDCharacterDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @Min(value=0, message = "Age must be positive")
    private int age;

    @NotBlank(message = "Class is required")
    private String ddClass;

    @NotBlank(message = "Race is required")
    private String ddRace;

    private List<DDEquipmentDTO> equipments = new ArrayList<>();

    public DDCharacterDTO(String name, int age, String ddClass, String ddRace, List<DDEquipmentDTO> equipments) {
        this.name = name;
        this.age = age;
        this.ddClass = ddClass;
        this.ddRace = ddRace;
        this.equipments = equipments;
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

    public String getDdClass() {
        return ddClass;
    }

    public void setDdClass(String ddClass) {
        this.ddClass = ddClass;
    }

    public List<DDEquipmentDTO> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<DDEquipmentDTO> equipments) {
        this.equipments = equipments;
    }

    public String getDdRace() {
        return ddRace;
    }

    public void setDdRace(String ddRace) {
        this.ddRace = ddRace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DDCharacterDTO that = (DDCharacterDTO) o;
        return age == that.age && Objects.equals(name, that.name) && Objects.equals(ddClass, that.ddClass) && Objects.equals(ddRace, that.ddRace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, ddClass, ddRace);
    }
}
