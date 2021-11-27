package com.example.DungeonsDragons.controllers;

import com.example.DungeonsDragons.model.DDClass;
import com.example.DungeonsDragons.services.DDClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class DDClassController {
    private DDClassService classService;

    public DDClassController(DDClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/classes")
    public ResponseEntity<List<DDClass>> getAllClasses() {
        return ResponseEntity.ok(classService.getAllClasses());
    }
}
