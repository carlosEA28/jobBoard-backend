package br.com.carlos.JobBoard_backend.repository;

import br.com.carlos.JobBoard_backend.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
    Optional<List<JobEntity>> findAllByJobType(String jobType);

    Optional<List<JobEntity>> findAllByLocation(String location);

    Optional<List<JobEntity>> findAllByJobCategory(String jobCategory);

    Optional<List<JobEntity>> findAllBySalaryRange(Integer range);

    Optional<List<JobEntity>> findAllBySalaryBased(String based);
}
