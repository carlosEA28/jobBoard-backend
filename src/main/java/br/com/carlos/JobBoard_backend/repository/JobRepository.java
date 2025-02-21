package br.com.carlos.JobBoard_backend.repository;

import br.com.carlos.JobBoard_backend.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
}
