package com.example.DungeonsDragons.services;

import com.example.DungeonsDragons.model.DDClass;
import com.example.DungeonsDragons.repositories.DDClassRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DDClassService {
    private final DDClassRepository classRepository;

    public DDClassService(DDClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public List<DDClass> getAllClasses() {
        List<DDClass> ddClasses = classRepository.findAll();
        return ddClasses;
    }
}
