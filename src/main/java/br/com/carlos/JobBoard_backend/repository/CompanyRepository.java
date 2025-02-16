package br.com.carlos.JobBoard_backend.repository;

import br.com.carlos.JobBoard_backend.entity.CompanyEntity;
import br.com.carlos.JobBoard_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
    Optional<CompanyEntity> findBybusinessEmail(String businessEmail);
}
