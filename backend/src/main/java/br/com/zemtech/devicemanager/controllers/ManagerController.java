package br.com.zemtech.devicemanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.zemtech.devicemanager.dtos.ManagerDto;
import br.com.zemtech.devicemanager.services.ManagerService;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping
    public ResponseEntity<List<ManagerDto>> findAll() {
        List<ManagerDto> managers = managerService.findAll();
        return ResponseEntity.ok(managers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManagerDto> findById(@PathVariable Long id) {
        ManagerDto manager = managerService.findById(id);
        return ResponseEntity.ok(manager);
    }

    @GetMapping("/manager-user/{managerUserId}")
    public ResponseEntity<List<ManagerDto>> findByManagerUserId(@PathVariable Long managerUserId) {
        List<ManagerDto> managers = managerService.findByManagerUserId(managerUserId);
        return ResponseEntity.ok(managers);
    }

    @GetMapping("/supervised-user/{supervisedUserId}")
    public ResponseEntity<List<ManagerDto>> findBySupervisedUserId(@PathVariable Long supervisedUserId) {
        List<ManagerDto> managers = managerService.findBySupervisedUserId(supervisedUserId);
        return ResponseEntity.ok(managers);
    }

    @PostMapping
    public ResponseEntity<ManagerDto> create(@RequestBody ManagerDto dto) {
        ManagerDto created = managerService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManagerDto> update(@PathVariable Long id, @RequestBody ManagerDto dto) {
        ManagerDto updated = managerService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        managerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
