package br.com.zemtech.devicemanager.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.zemtech.devicemanager.dtos.LocalizationDto;
import br.com.zemtech.devicemanager.exceptions.EntityNotExistsException;
import br.com.zemtech.devicemanager.models.Localization;
import br.com.zemtech.devicemanager.models.User;
import br.com.zemtech.devicemanager.repositories.LocalizationRepository;
import br.com.zemtech.devicemanager.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LocalizationService {

    @Autowired
    private LocalizationRepository localizationRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<LocalizationDto> findAll() {
        log.info("LocalizationService.findAll() - Iniciando busca de todas as localizações");
        List<LocalizationDto> localizations = localizationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        log.info("LocalizationService.findAll() - Retornando {} localizações", localizations.size());
        return localizations;
    }

    @Transactional(readOnly = true)
    public LocalizationDto findById(Long id) {
        log.info("LocalizationService.findById() - Iniciando busca de localização id: {}", id);
        Localization localization = localizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotExistsException("Localização não encontrada com id: " + id));
        LocalizationDto dto = convertToDTO(localization);
        log.info("LocalizationService.findById() - Retornando localização: {}", dto);
        return dto;
    }

    @Transactional(readOnly = true)
    public List<LocalizationDto> findByUserId(Long userId) {
        log.info("LocalizationService.findByUserId() - Iniciando busca de localizações para userId: {}", userId);
        List<LocalizationDto> localizations = localizationRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        log.info("LocalizationService.findByUserId() - Retornando {} localizações para userId: {}", localizations.size(), userId);
        return localizations;
    }

    @Transactional
    public LocalizationDto create(LocalizationDto dto) {
        log.info("LocalizationService.create() - Iniciando criação de localização: {}", dto);
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotExistsException("Usuário não encontrado com id: " + dto.getUserId()));
        
        Localization localization = new Localization();
        localization.setLatitude(dto.getLatitude());
        localization.setLongitude(dto.getLongitude());
        localization.setAltitude(dto.getAltitude());
        localization.setTimestamp(dto.getTimestamp());
        localization.setCep(dto.getCep());
        localization.setCity(dto.getCity());
        localization.setState(dto.getState());
        localization.setCountry(dto.getCountry());
        localization.setUser(user);

        Localization saved = localizationRepository.save(localization);
        LocalizationDto result = convertToDTO(saved);
        log.info("LocalizationService.create() - Localização criada com sucesso: {}", result);
        return result;
    }

    @Transactional
    public LocalizationDto update(Long id, LocalizationDto dto) {
        log.info("LocalizationService.update() - Iniciando atualização de localização id: {} com dados: {}", id, dto);
        Localization localization = localizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotExistsException("Localização não encontrada com id: " + id));

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotExistsException("Usuário não encontrado com id: " + dto.getUserId()));
            localization.setUser(user);
        }

        if (dto.getLatitude() != null) localization.setLatitude(dto.getLatitude());
        if (dto.getLongitude() != null) localization.setLongitude(dto.getLongitude());
        if (dto.getAltitude() != null) localization.setAltitude(dto.getAltitude());
        if (dto.getTimestamp() != null) localization.setTimestamp(dto.getTimestamp());
        if (dto.getCep() != null) localization.setCep(dto.getCep());
        if (dto.getCity() != null) localization.setCity(dto.getCity());
        if (dto.getState() != null) localization.setState(dto.getState());
        if (dto.getCountry() != null) localization.setCountry(dto.getCountry());

        Localization updated = localizationRepository.save(localization);
        LocalizationDto result = convertToDTO(updated);
        log.info("LocalizationService.update() - Localização atualizada com sucesso: {}", result);
        return result;
    }

    @Transactional
    public void delete(Long id) {
        log.info("LocalizationService.delete() - Iniciando exclusão de localização id: {}", id);
        if (!localizationRepository.existsById(id)) {
            throw new EntityNotExistsException("Localização não encontrada com id: " + id);
        }
        localizationRepository.deleteById(id);
        log.info("LocalizationService.delete() - Localização id: {} excluída com sucesso", id);
    }

    private LocalizationDto convertToDTO(Localization localization) {
        LocalizationDto dto = new LocalizationDto();
        dto.setId(localization.getId());
        dto.setLatitude(localization.getLatitude());
        dto.setLongitude(localization.getLongitude());
        dto.setAltitude(localization.getAltitude());
        dto.setTimestamp(localization.getTimestamp());
        dto.setCep(localization.getCep());
        dto.setCity(localization.getCity());
        dto.setState(localization.getState());
        dto.setCountry(localization.getCountry());
        dto.setUserId(localization.getUser().getId());
        return dto;
    }

}
