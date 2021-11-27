package com.example.DungeonsDragons.repositories;

import com.example.DungeonsDragons.model.DDEquipmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DDEquipmentCategoryRepository extends JpaRepository<DDEquipmentCategory, Long> {
    DDEquipmentCategory findByIndex(String index);
}
