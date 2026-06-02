package br.com.zemtech.devicemanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zemtech.devicemanager.models.Localization;

@Repository
public interface LocalizationRepository extends JpaRepository<Localization, Long> {

    List<Localization> findByUserId(Long userId);

}
