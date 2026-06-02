package br.com.zemtech.devicemanager.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.zemtech.devicemanager.dtos.AuthenticationInterface;
import br.com.zemtech.devicemanager.dtos.UserDto;
import br.com.zemtech.devicemanager.exceptions.DuplicateEntityException;
import br.com.zemtech.devicemanager.exceptions.EntityNotExistsException;
import br.com.zemtech.devicemanager.models.User;
import br.com.zemtech.devicemanager.models.UserRole;
import br.com.zemtech.devicemanager.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserRoleService userRoleService;
    
    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        log.info("UserService.findAll() - Iniciando busca de todos os usuários");
        List<UserDto> users = userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        log.info("UserService.findAll() - Retornando {} usuários", users.size());
        return users;
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        log.info("UserService.findById() - Iniciando busca de usuário com id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotExistsException("Usuário não encontrado com id: " + id));
        UserDto dto = convertToDTO(user);
        log.info("UserService.findById() - Retornando usuário: {}", dto);
        return dto;
    }

    @Transactional(readOnly = true)
    public UserDto findByEmail(String email) {
        log.info("UserService.findByEmail() - Iniciando busca de usuário com email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotExistsException("Usuário não encontrado com email: " + email));
        UserDto dto = convertToDTO(user);
        log.info("UserService.findByEmail() - Retornando usuário: {}", dto);
        return dto;
    }

    @Transactional
    public UserDto create(UserDto dto) {
        log.info("UserService.create() - Iniciando criação de usuário: {}", dto);
        
        // Verifica se já existe usuário com o mesmo email
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateEntityException("Já existe um usuário com o email: " + dto.getEmail());
        }
        
        // Verifica se já existe usuário com o mesmo username
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateEntityException("Já existe um usuário com o username: " + dto.getUsername());
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setDeviceName(dto.getDeviceName());

        User saved = userRepository.save(user);
        UserDto result = convertToDTO(saved);
        log.info("UserService.create() - Usuário criado com sucesso: {}", result);
        return result;
    }

    @Transactional
    public UserDto update(Long id, UserDto dto) {
        log.info("UserService.update() - Iniciando atualização de usuário id: {} com dados: {}", id, dto);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotExistsException("Usuário não encontrado com id: " + id));

        // Verifica email duplicado (se estiver mudando)
        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new DuplicateEntityException("Já existe um usuário com o email: " + dto.getEmail());
            }
            user.setEmail(dto.getEmail());
        }

        // Verifica username duplicado (se estiver mudando)
        if (dto.getUsername() != null && !dto.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(dto.getUsername())) {
                throw new DuplicateEntityException("Já existe um usuário com o username: " + dto.getUsername());
            }
            user.setUsername(dto.getUsername());
        }

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        
        if (dto.getDeviceName() != null) {
            user.setDeviceName(dto.getDeviceName());
        }

        User updated = userRepository.save(user);
        UserDto result = convertToDTO(updated);
        log.info("UserService.update() - Usuário atualizado com sucesso: {}", result);
        return result;
    }

    @Transactional
    public void delete(Long id) {
        log.info("UserService.delete() - Iniciando exclusão de usuário id: {}", id);
        if (!userRepository.existsById(id)) {
            throw new EntityNotExistsException("Usuário não encontrado com id: " + id);
        }
        userRepository.deleteById(id);
        log.info("UserService.delete() - Usuário id: {} excluído com sucesso", id);
    }

    /**
     * Autentica um usuário verificando email e senha
     * @param email Email do usuário
     * @param rawPassword Senha em texto plano para verificação
     * @return UserDto se autenticação bem-sucedida
     * @throws EntityNotExistsException se usuário não for encontrado
     * @throws org.springframework.security.authentication.BadCredentialsException se senha for inválida
     */
    @Transactional(readOnly = true)
    public UserDto authenticate(String email, String rawPassword) {
        log.info("UserService.authenticate() - Tentando autenticar usuário com email: {}", email);
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotExistsException("Usuário não encontrado com email: " + email));
        
        // Verifica se a senha fornecida corresponde à senha criptografada
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            log.warn("UserService.authenticate() - Senha inválida para email: {}", email);
            throw new org.springframework.security.authentication.BadCredentialsException("Senha inválida");
        }
        
        UserDto result = convertToDTO(user);
        log.info("UserService.authenticate() - Usuário autenticado com sucesso: {}", result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<AuthenticationInterface> dto = userRepository.findByUsername(username);

        if (dto.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        User user = new User();
        user.setEmail(dto.get(0).getEmail());
        user.setPassword(dto.get(0).getPassword());

        if (user.getUserRoles() == null) {
            user.setUserRoles(new ArrayList<>());
        }

        for (AuthenticationInterface role : dto) {
            UserRole userRole = userRoleService.findById(role.getIdRole());
            user.getUserRoles().add(userRole);
        }

        return user;
    }

    private UserDto convertToDTO(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setDeviceName(user.getDeviceName());
        
        // Incluir roles do usuário
        if (user.getUserRoles() != null && !user.getUserRoles().isEmpty()) {
            List<String> roles = user.getUserRoles().stream()
                    .map(userRole -> userRole.getRole().getName())
                    .collect(Collectors.toList());
            dto.setRoles(roles);
        }
        
        // Não incluir senha no DTO de retorno por segurança
        return dto;
    }

}
