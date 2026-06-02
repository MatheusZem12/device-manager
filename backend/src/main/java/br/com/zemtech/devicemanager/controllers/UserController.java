package br.com.zemtech.devicemanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.zemtech.devicemanager.dtos.LoginRequestDto;
import br.com.zemtech.devicemanager.dtos.LoginResponseDto;
import br.com.zemtech.devicemanager.dtos.UserDto;
import br.com.zemtech.devicemanager.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        UserDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> findByEmail(@PathVariable String email) {
        UserDto user = userService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto dto) {
        UserDto created = userService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto dto) {
        UserDto updated = userService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint de login/autenticação
     * Verifica email e senha do usuário
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            UserDto user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            
            LoginResponseDto response = new LoginResponseDto();
            response.setSuccess(true);
            response.setMessage("Login realizado com sucesso");
            response.setUser(user);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception ex) {
            LoginResponseDto response = new LoginResponseDto();
            response.setSuccess(false);
            response.setMessage(ex.getMessage());
            response.setUser(null);
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

}
