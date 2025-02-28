package br.com.carlos.JobBoard_backend.service;

import br.com.carlos.JobBoard_backend.dto.JobDto;
import br.com.carlos.JobBoard_backend.dto.UpdateJobDto;
import br.com.carlos.JobBoard_backend.exceptions.JobNotFound;
import br.com.carlos.JobBoard_backend.repository.JobRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public List<JobDto> getAllJobs() {
        var jobs = jobRepository.findAll();

        return jobs.stream()
                .map(job -> new JobDto(
                        job.getPhoneNumber(),
                        job.getEmployeesNumber(),
                        job.getJobTitle(),
                        job.getJobType(),
                        job.getJobCategory(),
                        job.getLocation(),
                        job.getEmploymentType(),
                        job.getAddressLine(),
                        job.getSalaryRange(),
                        job.getSalaryBased(),
                        job.getAboutCompany(),
                        job.getRequirements(),
                        job.getCompanyLogUrl()
                )).toList();
    }

    public List<JobDto> getJobByJobType(String jobType) {
        var jobs = jobRepository.findAllByJobType(jobType).orElseThrow();

        return jobs.stream()
                .map(job -> new JobDto(
                        job.getPhoneNumber(),
                        job.getEmployeesNumber(),
                        job.getJobTitle(),
                        job.getJobType(),
                        job.getJobCategory(),
                        job.getLocation(),
                        job.getEmploymentType(),
                        job.getAddressLine(),
                        job.getSalaryRange(),
                        job.getSalaryBased(),
                        job.getAboutCompany(),
                        job.getRequirements(),
                        job.getCompanyLogUrl()
                )).toList();
    }

    public List<JobDto> getJobByJobLocation(String location) {
        var jobs = jobRepository.findAllByLocation(location).orElseThrow();

        return jobs.stream()
                .map(job -> new JobDto(
                        job.getPhoneNumber(),
                        job.getEmployeesNumber(),
                        job.getJobTitle(),
                        job.getJobType(),
                        job.getJobCategory(),
                        job.getLocation(),
                        job.getEmploymentType(),
                        job.getAddressLine(),
                        job.getSalaryRange(),
                        job.getSalaryBased(),
                        job.getAboutCompany(),
                        job.getRequirements(),
                        job.getCompanyLogUrl()
                )).toList();
    }

    public List<JobDto> getJobBySalaryRange(Integer range) {
        var jobs = jobRepository.findAllBySalaryRange(range).orElseThrow();

        return jobs.stream()
                .map(job -> new JobDto(
                        job.getPhoneNumber(),
                        job.getEmployeesNumber(),
                        job.getJobTitle(),
                        job.getJobType(),
                        job.getJobCategory(),
                        job.getLocation(),
                        job.getEmploymentType(),
                        job.getAddressLine(),
                        job.getSalaryRange(),
                        job.getSalaryBased(),
                        job.getAboutCompany(),
                        job.getRequirements(),
                        job.getCompanyLogUrl()
                )).toList();
    }

    public List<JobDto> getJobBySalaryBased(String based) {
        var jobs = jobRepository.findAllBySalaryBased(based).orElseThrow();

        return jobs.stream()
                .map(job -> new JobDto(
                        job.getPhoneNumber(),
                        job.getEmployeesNumber(),
                        job.getJobTitle(),
                        job.getJobType(),
                        job.getJobCategory(),
                        job.getLocation(),
                        job.getEmploymentType(),
                        job.getAddressLine(),
                        job.getSalaryRange(),
                        job.getSalaryBased(),
                        job.getAboutCompany(),
                        job.getRequirements(),
                        job.getCompanyLogUrl()
                )).toList();
    }

    public List<JobDto> getJobByJobCategory(String category) {
        var jobs = jobRepository.findAllByJobCategory(category).orElseThrow();

        return jobs.stream()
                .map(job -> new JobDto(
                        job.getPhoneNumber(),
                        job.getEmployeesNumber(),
                        job.getJobTitle(),
                        job.getJobType(),
                        job.getJobCategory(),
                        job.getLocation(),
                        job.getEmploymentType(),
                        job.getAddressLine(),
                        job.getSalaryRange(),
                        job.getSalaryBased(),
                        job.getAboutCompany(),
                        job.getRequirements(),
                        job.getCompanyLogUrl()
                )).toList();
    }

    @Transactional
    public ResponseEntity<Void> updateJob(String jobId, UpdateJobDto dto) {
        // Validate input
        if (jobId == null || dto == null) {
            throw new IllegalArgumentException("Job ID and DTO must not be null");
        }

        // Retrieve the job entity
        var jobExists = jobRepository.findById(UUID.fromString(jobId))
                .orElseThrow(JobNotFound::new);

        // Update only non-null fields
        if (dto.phoneNumber() != null) {
            jobExists.setPhoneNumber(dto.phoneNumber());
        }
        if (dto.employeesNumber() != null) {
            jobExists.setEmployeesNumber(dto.employeesNumber());
        }
        if (dto.jobTitle() != null) {
            jobExists.setJobTitle(dto.jobTitle());
        }
        if (dto.jobCategory() != null) {
            jobExists.setJobCategory(dto.jobCategory());
        }
        if (dto.jobType() != null) {
            jobExists.setJobType(dto.jobType());
        }
        if (dto.location() != null) {
            jobExists.setLocation(dto.location());
        }
        if (dto.employmentType() != null) {
            jobExists.setEmploymentType(dto.employmentType());
        }
        if (dto.addressLine() != null) {
            jobExists.setAddressLine(dto.addressLine());
        }
        if (dto.salaryRange() != null) {
            jobExists.setSalaryRange(dto.salaryRange());
        }
        if (dto.salaryBased() != null) {
            jobExists.setSalaryBased(dto.salaryBased());
        }
        if (dto.requirements() != null) {
            jobExists.setRequirements(dto.requirements());
        }
        if (dto.aboutCompany() != null) {
            jobExists.setAboutCompany(dto.aboutCompany());
        }

        // Save the updated job
        jobRepository.save(jobExists);

        // Log the update
//        log.info("Job with ID {} updated successfully", jobId);

        // Return no content response
        return ResponseEntity.noContent().build();
    }
}
