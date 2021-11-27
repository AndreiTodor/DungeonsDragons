package com.example.DungeonsDragons.services;

import com.example.DungeonsDragons.DTOs.DDCharacterDTO;
import com.example.DungeonsDragons.DTOs.DDEquipmentItemDTO;
import com.example.DungeonsDragons.exceptions.DDClassNotExistantException;
import com.example.DungeonsDragons.exceptions.DDRaceNotExistantException;
import com.example.DungeonsDragons.model.*;
import com.example.DungeonsDragons.repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DDCharacterService {
    private final DDCharacterRepository characterRepository;
    private final DDEquipmentRepository equipmentRepository;
    private final DDEquipmentItemRepository equipmentItemRepository;
    private final DDClassRepository classRepository;
    private final DDRaceRepository raceRepository;

    DDCharacterService(
            DDCharacterRepository characterRepository,
            DDEquipmentRepository equipmentRepository,
            DDEquipmentItemRepository equipmentItemRepository,
            DDClassRepository classRepository,
            DDRaceRepository raceRepository
    ) {
        this.characterRepository = characterRepository;
        this.equipmentRepository = equipmentRepository;
        this.equipmentItemRepository = equipmentItemRepository;
        this.classRepository = classRepository;
        this.raceRepository = raceRepository;
    }

    public List<DDCharacter> getAllCharacters() {
        return characterRepository.findAll();
    }

    public Long createCharacter(DDCharacterDTO characterDTO) throws DDClassNotExistantException, DDRaceNotExistantException {
        List<DDEquipment> characterEquipment = new ArrayList<>();
        characterDTO.getEquipments().forEach(equipmentDTO -> {
            if (equipmentDTO.getId() != null) {
                DDEquipment existingEquipment = equipmentRepository.getById(equipmentDTO.getId());
                characterEquipment.add(existingEquipment);
            } else{
                String equipmentItemIndex = equipmentDTO.getItem().getIndex();
                int quantity = equipmentDTO.getQuantity();
                DDEquipmentItem equipmentItem = equipmentItemRepository.findByIndex(equipmentItemIndex);
                DDEquipment savedEquipment = equipmentRepository.save(new DDEquipment(quantity));
                savedEquipment.setItem(equipmentItem);
                characterEquipment.add(equipmentRepository.save(savedEquipment));
            }
        });
        DDRace existingRace = raceRepository.findByIndex(characterDTO.getDdRace());
        if (existingRace == null) {
            throw new DDRaceNotExistantException("Race not found in DB");
        }

        DDClass existingClass = classRepository.findByIndex(characterDTO.getDdClass());
        if (existingClass == null) {
            throw new DDClassNotExistantException("Class not found in DB");
        }

        DDCharacter savedCharacter = characterRepository.save(new DDCharacter(characterDTO.getName(), characterDTO.getAge()));
        savedCharacter.setDdClass(existingClass);
        savedCharacter.setDdRace(existingRace);
        savedCharacter.setEquipments(characterEquipment);
        characterRepository.save(savedCharacter);
        return savedCharacter.getId();
    }
}
