package com.example.DungeonsDragons.repositories;

import com.example.DungeonsDragons.model.DDRace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DDRaceRepository extends JpaRepository<DDRace, Long> {
    DDRace findByIndex(String index);
}
