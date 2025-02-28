package br.com.carlos.JobBoard_backend.service;

import br.com.carlos.JobBoard_backend.dto.JobDto;
import br.com.carlos.JobBoard_backend.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
