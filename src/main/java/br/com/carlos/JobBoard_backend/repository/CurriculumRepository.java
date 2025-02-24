package br.com.carlos.JobBoard_backend.repository;

import br.com.carlos.JobBoard_backend.entity.CurriculumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CurriculumRepository extends JpaRepository<CurriculumEntity, UUID> {

    Optional<CurriculumEntity> findByUser_UserId(UUID userId);


}
