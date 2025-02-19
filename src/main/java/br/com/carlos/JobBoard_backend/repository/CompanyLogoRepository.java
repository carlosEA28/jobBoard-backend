package br.com.carlos.JobBoard_backend.repository;

import br.com.carlos.JobBoard_backend.entity.CompanyLogoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyLogoRepository extends JpaRepository<CompanyLogoEntity, UUID> {
}
