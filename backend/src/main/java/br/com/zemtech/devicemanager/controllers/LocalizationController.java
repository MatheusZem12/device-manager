package br.com.zemtech.devicemanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.zemtech.devicemanager.dtos.LocalizationDto;
import br.com.zemtech.devicemanager.services.LocalizationService;

@RestController
@RequestMapping("/api/localizations")
public class LocalizationController {

    @Autowired
    private LocalizationService localizationService;

    @GetMapping
    public ResponseEntity<List<LocalizationDto>> findAll() {
        List<LocalizationDto> localizations = localizationService.findAll();
        return ResponseEntity.ok(localizations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalizationDto> findById(@PathVariable Long id) {
        LocalizationDto localization = localizationService.findById(id);
        return ResponseEntity.ok(localization);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LocalizationDto>> findByUserId(@PathVariable Long userId) {
        List<LocalizationDto> localizations = localizationService.findByUserId(userId);
        return ResponseEntity.ok(localizations);
    }

    @PostMapping
    public ResponseEntity<LocalizationDto> create(@RequestBody LocalizationDto dto) {
        LocalizationDto created = localizationService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocalizationDto> update(@PathVariable Long id, @RequestBody LocalizationDto dto) {
        LocalizationDto updated = localizationService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        localizationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
