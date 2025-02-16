package br.com.carlos.JobBoard_backend.controller;

import br.com.carlos.JobBoard_backend.dto.CreateCompanyDto;
import br.com.carlos.JobBoard_backend.entity.CompanyEntity;
import br.com.carlos.JobBoard_backend.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
