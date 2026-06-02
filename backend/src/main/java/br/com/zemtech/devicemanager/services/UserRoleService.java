package br.com.zemtech.devicemanager.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.zemtech.devicemanager.exceptions.EntityNotExistsException;
import br.com.zemtech.devicemanager.models.Role;
import br.com.zemtech.devicemanager.models.User;
import br.com.zemtech.devicemanager.models.UserRole;
import br.com.zemtech.devicemanager.repositories.RoleRepository;
import br.com.zemtech.devicemanager.repositories.UserRepository;
import br.com.zemtech.devicemanager.repositories.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserRoleService {

    private final UserRoleRepository repository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private String INTERFACE_NAME = "UserRole";

    public UserRoleService(UserRoleRepository repository, UserRepository userRepository, RoleRepository roleRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserRole> findAll() {
        log.info("UserRoleService.findAll() - Iniciando busca de todos os user-roles");
        List<UserRole> userRoles = repository.findAll();
        log.info("UserRoleService.findAll() - Retornando {} user-roles", userRoles.size());
        return userRoles;
    }

    public UserRole findById(Long id) {
        log.info("UserRoleService.findById() - Iniciando busca de user-role id: {}", id);
        UserRole userRole = repository.findById(id).orElseThrow(() -> new EntityNotExistsException(INTERFACE_NAME + " not found with id: " + id));
        log.info("UserRoleService.findById() - Retornando user-role: userId={}, roleId={}", 
                userRole.getUser() != null ? userRole.getUser().getId() : null, 
                userRole.getRole() != null ? userRole.getRole().getId() : null);
        return userRole;
    }

    public UserRole create(UserRole userRole) {
        log.info("UserRoleService.create() - Iniciando criação de user-role: userId={}, roleId={}", 
                userRole.getUser() != null ? userRole.getUser().getId() : null, 
                userRole.getRole() != null ? userRole.getRole().getId() : null);
        
        if (userRole.getUser() != null && userRole.getUser().getId() != null) {
            User user = userRepository.findById(userRole.getUser().getId())
                .orElseThrow(() -> new EntityNotExistsException("User not found with id: " + userRole.getUser().getId()));
            userRole.setUser(user);
        }
        if (userRole.getRole() != null && userRole.getRole().getId() != null) {
            Role role = roleRepository.findById(userRole.getRole().getId())
                .orElseThrow(() -> new EntityNotExistsException("Role not found with id: " + userRole.getRole().getId()));
            userRole.setRole(role);
        }
        UserRole saved = repository.save(userRole);
        log.info("UserRoleService.create() - User-role criado com sucesso: id={}, userId={}, roleId={}", 
                saved.getId(), 
                saved.getUser() != null ? saved.getUser().getId() : null, 
                saved.getRole() != null ? saved.getRole().getId() : null);
        return saved;
    }

    public UserRole update(UserRole userRole) {
        log.info("UserRoleService.update() - Iniciando atualização de user-role id: {}", userRole.getId());
        if (userRole.getId() == null) throw new EntityNotExistsException("UserRole id required");
        UserRole existing = findById(userRole.getId());
        
        if (userRole.getUser() != null && userRole.getUser().getId() != null) {
            User user = userRepository.findById(userRole.getUser().getId())
                .orElseThrow(() -> new EntityNotExistsException("User not found with id: " + userRole.getUser().getId()));
            existing.setUser(user);
        }
        if (userRole.getRole() != null && userRole.getRole().getId() != null) {
            Role role = roleRepository.findById(userRole.getRole().getId())
                .orElseThrow(() -> new EntityNotExistsException("Role not found with id: " + userRole.getRole().getId()));
            existing.setRole(role);
        }
        UserRole updated = repository.save(existing);
        log.info("UserRoleService.update() - User-role atualizado com sucesso: id={}, userId={}, roleId={}", 
                updated.getId(), 
                updated.getUser() != null ? updated.getUser().getId() : null, 
                updated.getRole() != null ? updated.getRole().getId() : null);
        return updated;
    }

    public void delete(Long id) {
        log.info("UserRoleService.delete() - Iniciando exclusão de user-role id: {}", id);
        UserRole existing = findById(id);
        repository.delete(existing);
        log.info("UserRoleService.delete() - User-role id: {} excluído com sucesso", id);
    }
}
