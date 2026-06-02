package br.com.zemtech.devicemanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.zemtech.devicemanager.models.UserRole;
import br.com.zemtech.devicemanager.services.UserRoleService;

@RestController
@RequestMapping("/api/user-roles")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping
    public ResponseEntity<List<UserRole>> findAll() {
        List<UserRole> userRoles = userRoleService.findAll();
        return ResponseEntity.ok(userRoles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRole> findById(@PathVariable Long id) {
        UserRole userRole = userRoleService.findById(id);
        return ResponseEntity.ok(userRole);
    }

    @PostMapping
    public ResponseEntity<UserRole> create(@RequestBody UserRole userRole) {
        UserRole created = userRoleService.create(userRole);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRole> update(@RequestBody UserRole userRole) {
        UserRole updated = userRoleService.update(userRole);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userRoleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
