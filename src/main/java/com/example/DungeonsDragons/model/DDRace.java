package com.example.DungeonsDragons.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dd_race")
public class DDRace {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String index;

    @Column(nullable = false)
    private String name;

    public DDRace() {};

    public DDRace(String index, String name) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DDRace ddRace = (DDRace) o;
        return index.equals(ddRace.index) && name.equals(ddRace.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, name);
    }
}
