package br.com.zemtech.devicemanager.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.zemtech.devicemanager.dtos.ManagerDto;
import br.com.zemtech.devicemanager.exceptions.EntityNotExistsException;
import br.com.zemtech.devicemanager.models.Manager;
import br.com.zemtech.devicemanager.models.User;
import br.com.zemtech.devicemanager.repositories.ManagerRepository;
import br.com.zemtech.devicemanager.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ManagerDto> findAll() {
        log.info("ManagerService.findAll() - Iniciando busca de todos os gerenciamentos");
        List<ManagerDto> managers = managerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        log.info("ManagerService.findAll() - Retornando {} gerenciamentos", managers.size());
        return managers;
    }

    @Transactional(readOnly = true)
    public ManagerDto findById(Long id) {
        log.info("ManagerService.findById() - Iniciando busca de gerenciamento id: {}", id);
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new EntityNotExistsException("Gerenciamento não encontrado com id: " + id));
        ManagerDto dto = convertToDTO(manager);
        log.info("ManagerService.findById() - Retornando gerenciamento: {}", dto);
        return dto;
    }

    @Transactional(readOnly = true)
    public List<ManagerDto> findByManagerUserId(Long managerUserId) {
        log.info("ManagerService.findByManagerUserId() - Iniciando busca de gerenciamentos para managerUserId: {}", managerUserId);
        List<ManagerDto> managers = managerRepository.findByManagerUserId(managerUserId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        log.info("ManagerService.findByManagerUserId() - Retornando {} gerenciamentos para managerUserId: {}", managers.size(), managerUserId);
        return managers;
    }

    @Transactional(readOnly = true)
    public List<ManagerDto> findBySupervisedUserId(Long supervisedUserId) {
        log.info("ManagerService.findBySupervisedUserId() - Iniciando busca de gerenciamentos para supervisedUserId: {}", supervisedUserId);
        List<ManagerDto> managers = managerRepository.findBySupervisedUserId(supervisedUserId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        log.info("ManagerService.findBySupervisedUserId() - Retornando {} gerenciamentos para supervisedUserId: {}", managers.size(), supervisedUserId);
        return managers;
    }

    @Transactional
    public ManagerDto create(ManagerDto dto) {
        log.info("ManagerService.create() - Iniciando criação de gerenciamento: {}", dto);
        User managerUser = userRepository.findById(dto.getManagerUserId())
                .orElseThrow(() -> new EntityNotExistsException("Usuário gerente não encontrado com id: " + dto.getManagerUserId()));
        
        User supervisedUser = userRepository.findById(dto.getSupervisedUserId())
                .orElseThrow(() -> new EntityNotExistsException("Usuário supervisionado não encontrado com id: " + dto.getSupervisedUserId()));

        Manager manager = new Manager();
        manager.setManagerUser(managerUser);
        manager.setSupervisedUser(supervisedUser);

        Manager saved = managerRepository.save(manager);
        ManagerDto result = convertToDTO(saved);
        log.info("ManagerService.create() - Gerenciamento criado com sucesso: {}", result);
        return result;
    }

    @Transactional
    public ManagerDto update(Long id, ManagerDto dto) {
        log.info("ManagerService.update() - Iniciando atualização de gerenciamento id: {} com dados: {}", id, dto);
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new EntityNotExistsException("Gerenciamento não encontrado com id: " + id));

        if (dto.getManagerUserId() != null) {
            User managerUser = userRepository.findById(dto.getManagerUserId())
                    .orElseThrow(() -> new EntityNotExistsException("Usuário gerente não encontrado com id: " + dto.getManagerUserId()));
            manager.setManagerUser(managerUser);
        }

        if (dto.getSupervisedUserId() != null) {
            User supervisedUser = userRepository.findById(dto.getSupervisedUserId())
                    .orElseThrow(() -> new EntityNotExistsException("Usuário supervisionado não encontrado com id: " + dto.getSupervisedUserId()));
            manager.setSupervisedUser(supervisedUser);
        }

        Manager updated = managerRepository.save(manager);
        ManagerDto result = convertToDTO(updated);
        log.info("ManagerService.update() - Gerenciamento atualizado com sucesso: {}", result);
        return result;
    }

    @Transactional
    public void delete(Long id) {
        log.info("ManagerService.delete() - Iniciando exclusão de gerenciamento id: {}", id);
        if (!managerRepository.existsById(id)) {
            throw new EntityNotExistsException("Gerenciamento não encontrado com id: " + id);
        }
        managerRepository.deleteById(id);
        log.info("ManagerService.delete() - Gerenciamento id: {} excluído com sucesso", id);
    }

    private ManagerDto convertToDTO(Manager manager) {
        ManagerDto dto = new ManagerDto();
        dto.setId(manager.getId());
        dto.setManagerUserId(manager.getManagerUser().getId());
        dto.setSupervisedUserId(manager.getSupervisedUser().getId());
        dto.setManagerUsername(manager.getManagerUser().getUsername());
        dto.setSupervisedUsername(manager.getSupervisedUser().getUsername());
        return dto;
    }

}
