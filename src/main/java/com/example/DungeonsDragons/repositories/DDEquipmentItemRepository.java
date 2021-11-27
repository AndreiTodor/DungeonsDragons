package com.example.DungeonsDragons.repositories;

import com.example.DungeonsDragons.model.DDEquipmentItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DDEquipmentItemRepository extends JpaRepository<DDEquipmentItem, Long> {
    DDEquipmentItem findByIndex(String index);
}
