package com.example.DungeonsDragons.repositories;

import com.example.DungeonsDragons.model.DDCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DDCharacterRepository extends JpaRepository<DDCharacter, Long> {
}
