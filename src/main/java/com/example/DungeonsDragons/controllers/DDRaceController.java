package com.example.DungeonsDragons.controllers;

import com.example.DungeonsDragons.model.DDRace;
import com.example.DungeonsDragons.services.DDRaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class DDRaceController {
    private final DDRaceService raceService;

    public DDRaceController(DDRaceService raceService) {
        this.raceService = raceService;
    }

    @GetMapping("/races")
    public ResponseEntity<List<DDRace>> getAllRaces() {
        return ResponseEntity.ok(raceService.getAllRaces());
    }
}
