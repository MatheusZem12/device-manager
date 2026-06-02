package br.com.zemtech.devicemanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zemtech.devicemanager.models.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    List<Manager> findByManagerUserId(Long managerUserId);
    List<Manager> findBySupervisedUserId(Long supervisedUserId);

}
