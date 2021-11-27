package com.example.DungeonsDragons.model;

import javax.persistence.*;

@Entity
@Table(name = "dd_equipment")
public class DDEquipment {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="item_id")
    private DDEquipmentItem item;

    private int quantity;

    public DDEquipment() {};
    public DDEquipment(int quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DDEquipmentItem getItem() {
        return item;
    }

    public void setItem(DDEquipmentItem item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
