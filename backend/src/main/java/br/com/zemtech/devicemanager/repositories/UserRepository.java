package br.com.zemtech.devicemanager.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.zemtech.devicemanager.dtos.AuthenticationInterface;
import br.com.zemtech.devicemanager.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(
        nativeQuery = true,
        value =
        "SELECT u.email as email, u.senha as senha, ur.role_id as idRole "
        + "FROM tb_usuarios u INNER JOIN tb_usuarios_roles ur ON u.id = ur.user_id "
        + "WHERE u.email = ?1"
    )
    List<AuthenticationInterface> findByUsername(String username);

    Optional<User> findByEmail(String email);
    
    Optional<User> findByUsernameIgnoreCase(String username);
    
    boolean existsByEmail(String email);
    
    boolean existsByUsername(String username);

}
