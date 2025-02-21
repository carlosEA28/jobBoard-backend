package br.com.carlos.JobBoard_backend.controller;

import br.com.carlos.JobBoard_backend.dto.CreateCompanyDto;
import br.com.carlos.JobBoard_backend.dto.JobDto;
import br.com.carlos.JobBoard_backend.dto.LoginDto;
import br.com.carlos.JobBoard_backend.dto.LoginResponseDto;
import br.com.carlos.JobBoard_backend.entity.CompanyEntity;
import br.com.carlos.JobBoard_backend.entity.JobEntity;
import br.com.carlos.JobBoard_backend.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/register")
    public ResponseEntity<CompanyEntity> createCompany(@Valid @RequestBody CreateCompanyDto dto) {
        var company = companyService.createCompanyAccount(dto);

        return ResponseEntity.ok().body(company);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginCompany(@Valid @RequestBody LoginDto dto) {
        var token = companyService.loginCompany(dto);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/post/{companyId}")
    public ResponseEntity<JobEntity> postJob(@Valid @RequestBody JobDto dto, @PathVariable("companyId") String companyId) {
        var job = companyService.postJob(dto, companyId);
        return ResponseEntity.ok().body(job);
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<JobDto>> getAllJobs() {
        var jobs = companyService.getAllJobs();

        return ResponseEntity.ok().body(jobs);
    }

}
