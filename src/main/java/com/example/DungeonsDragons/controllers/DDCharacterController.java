package com.example.DungeonsDragons.controllers;

import com.example.DungeonsDragons.DTOs.DDCharacterDTO;
import com.example.DungeonsDragons.exceptions.DDClassNotExistantException;
import com.example.DungeonsDragons.exceptions.DDRaceNotExistantException;
import com.example.DungeonsDragons.model.DDCharacter;
import com.example.DungeonsDragons.services.DDCharacterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/api")
public class DDCharacterController {
    private final DDCharacterService ddCharacterService;

    DDCharacterController(DDCharacterService service) {
        this.ddCharacterService = service;
    }

    @GetMapping("/characters")
    public ResponseEntity<List<DDCharacter>> getAllCharacters() {
        return ResponseEntity.ok(ddCharacterService.getAllCharacters());
    }

    @PostMapping("/characters")
    ResponseEntity<Long> createCharacter(@Valid @RequestBody DDCharacterDTO characterDTO) throws DDClassNotExistantException, DDRaceNotExistantException {
            Long id = ddCharacterService.createCharacter(characterDTO);
            return ResponseEntity.ok(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DDClassNotExistantException.class)
    public String handleDDClassNotExistantExceptions(
            DDClassNotExistantException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DDRaceNotExistantException.class)
    public String handleDDRaceNotExistantException(
            DDRaceNotExistantException ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
