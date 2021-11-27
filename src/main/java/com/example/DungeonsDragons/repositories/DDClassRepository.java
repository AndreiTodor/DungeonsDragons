package com.example.DungeonsDragons.repositories;

import com.example.DungeonsDragons.model.DDClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DDClassRepository extends JpaRepository<DDClass, Long> {
    DDClass findByIndex(String index);
}
