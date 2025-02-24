package br.com.carlos.JobBoard_backend.service;

import br.com.carlos.JobBoard_backend.dto.CreateCompanyDto;
import br.com.carlos.JobBoard_backend.dto.JobDto;
import br.com.carlos.JobBoard_backend.dto.LoginDto;
import br.com.carlos.JobBoard_backend.dto.LoginResponseDto;
import br.com.carlos.JobBoard_backend.entity.CompanyEntity;
import br.com.carlos.JobBoard_backend.entity.JobEntity;
import br.com.carlos.JobBoard_backend.enums.AuthProvider;
import br.com.carlos.JobBoard_backend.enums.Roles;
import br.com.carlos.JobBoard_backend.exceptions.CompanyAlreadyExists;
import br.com.carlos.JobBoard_backend.exceptions.CompanyNotFound;
import br.com.carlos.JobBoard_backend.exceptions.WrongCreadentials;
import br.com.carlos.JobBoard_backend.repository.CompanyLogoRepository;
import br.com.carlos.JobBoard_backend.repository.CompanyRepository;
import br.com.carlos.JobBoard_backend.repository.JobRepository;
import br.com.carlos.JobBoard_backend.utils.JwtActions;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyLogoRepository companyLogoRepository;

    @Autowired
    private JwtActions jwtActions;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CompanyService(BCryptPasswordEncoder passwordEncoder) {
        this.bCryptPasswordEncoder = passwordEncoder;
    }


    public CompanyEntity createCompanyAccount(CreateCompanyDto dto) {

        companyRepository.findByBusinessEmail(dto.businessEmail())
                .ifPresent(company -> {
                    throw new CompanyAlreadyExists();
                });

        var newCompany = new CompanyEntity();

        newCompany.setCompanyName(dto.name());
        newCompany.setBusinessEmail(dto.businessEmail());
        newCompany.setPassword(bCryptPasswordEncoder.encode(dto.password()));
        newCompany.setRoles(Roles.COMPANY);
        newCompany.setAuthProvider(AuthProvider.LOCAL);

        return companyRepository.save(newCompany);
    }

    public LoginResponseDto loginCompany(LoginDto dto) {
        var company = companyRepository.findByBusinessEmail(dto.email()).orElseThrow(CompanyNotFound::new);

        if (!bCryptPasswordEncoder.matches(dto.password(), company.getPassword())) {
            throw new WrongCreadentials("Email/Password incorrect");
        }

        return jwtActions.jwtCreate(dto);
    }

    @Transactional
    public JobEntity postJob(JobDto dto, String companyId) {
        var company = companyRepository.findById(UUID.fromString(companyId))
                .orElseThrow(CompanyNotFound::new);

        var logo = companyLogoRepository.findById(UUID.fromString(dto.logoId()))
                .orElseThrow(() -> new IllegalArgumentException("Logo not found for ID: " + dto.logoId()));


        var newJob = new JobEntity();
        newJob.setPhoneNumber(dto.phoneNumber());
        newJob.setEmployeesNumber(dto.employeesNumber());
        newJob.setJobTitle(dto.jobTitle());
        newJob.setJobCategory(dto.jobCategory());
        newJob.setJobType(dto.jobType());
        newJob.setLocation(dto.location());
        newJob.setEmploymentType(dto.employmentType());
        newJob.setAddressLine(dto.addressLine());
        newJob.setSalaryRange(dto.salaryRange());
        newJob.setSalaryBased(dto.salaryBased());
        newJob.setRequirements(dto.requirements());
        newJob.setAboutCompany(dto.aboutCompany());
        newJob.setCompany(company);
        newJob.setCompanyLogUrl(logo.getUrl());

        return jobRepository.save(newJob);

    }

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
}
