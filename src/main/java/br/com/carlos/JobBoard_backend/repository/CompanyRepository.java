package br.com.carlos.JobBoard_backend.repository;

import br.com.carlos.JobBoard_backend.entity.CompanyEntity;
import br.com.carlos.JobBoard_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
    Optional<CompanyEntity> findByBusinessEmail(String businessEmail);
}