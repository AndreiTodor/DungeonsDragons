package com.example.DungeonsDragons.services;

import com.example.DungeonsDragons.model.DDRace;
import com.example.DungeonsDragons.repositories.DDRaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DDRaceService {
    private final DDRaceRepository raceRepository;

    public DDRaceService(DDRaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    public List<DDRace> getAllRaces() {
        return this.raceRepository.findAll();
    }
}
