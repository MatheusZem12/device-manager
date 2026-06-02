package br.com.zemtech.devicemanager.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.zemtech.devicemanager.dtos.RoleDto;
import br.com.zemtech.devicemanager.exceptions.EntityNotExistsException;
import br.com.zemtech.devicemanager.models.Role;
import br.com.zemtech.devicemanager.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<RoleDto> findAll() {
        log.info("RoleService.findAll() - Iniciando busca de todas as roles");
        List<RoleDto> roles = roleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        log.info("RoleService.findAll() - Retornando {} roles", roles.size());
        return roles;
    }

    @Transactional(readOnly = true)
    public RoleDto findById(Long id) {
        log.info("RoleService.findById() - Iniciando busca de role id: {}", id);
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotExistsException("Role não encontrada com id: " + id));
        RoleDto dto = convertToDTO(role);
        log.info("RoleService.findById() - Retornando role: {}", dto);
        return dto;
    }

    @Transactional(readOnly = true)
    public RoleDto findByName(String name) {
        log.info("RoleService.findByName() - Iniciando busca de role nome: {}", name);
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new EntityNotExistsException("Role não encontrada com nome: " + name));
        RoleDto dto = convertToDTO(role);
        log.info("RoleService.findByName() - Retornando role: {}", dto);
        return dto;
    }

    @Transactional
    public RoleDto create(RoleDto dto) {
        log.info("RoleService.create() - Iniciando criação de role: {}", dto);
        Role role = new Role();
        role.setName(dto.getName());

        Role saved = roleRepository.save(role);
        RoleDto result = convertToDTO(saved);
        log.info("RoleService.create() - Role criada com sucesso: {}", result);
        return result;
    }

    @Transactional
    public RoleDto update(Long id, RoleDto dto) {
        log.info("RoleService.update() - Iniciando atualização de role id: {} com dados: {}", id, dto);
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotExistsException("Role não encontrada com id: " + id));

        if (dto.getName() != null) role.setName(dto.getName());

        Role updated = roleRepository.save(role);
        RoleDto result = convertToDTO(updated);
        log.info("RoleService.update() - Role atualizada com sucesso: {}", result);
        return result;
    }

    @Transactional
    public void delete(Long id) {
        log.info("RoleService.delete() - Iniciando exclusão de role id: {}", id);
        if (!roleRepository.existsById(id)) {
            throw new EntityNotExistsException("Role não encontrada com id: " + id);
        }
        roleRepository.deleteById(id);
        log.info("RoleService.delete() - Role id: {} excluída com sucesso", id);
    }

    private RoleDto convertToDTO(Role role) {
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }

}
