package br.com.carlos.JobBoard_backend.service;

import br.com.carlos.JobBoard_backend.dto.CreateCompanyDto;
import br.com.carlos.JobBoard_backend.entity.CompanyEntity;
import br.com.carlos.JobBoard_backend.entity.JobEntity;
import br.com.carlos.JobBoard_backend.enums.AuthProvider;
import br.com.carlos.JobBoard_backend.enums.Roles;
import br.com.carlos.JobBoard_backend.exceptions.CompanyAlreadyExists;
import br.com.carlos.JobBoard_backend.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity createCompanyAccount(CreateCompanyDto dto) {

        companyRepository.findBybusinessEmail(dto.businessEmail())
                .ifPresent(company -> {
                    throw new CompanyAlreadyExists();
                });

        var newCompany = new CompanyEntity();

        newCompany.setCompanyName(dto.name());
        newCompany.setBusinessEmail(dto.businessEmail());
        newCompany.setPassword(dto.password());
        newCompany.setRoles(Roles.COMPANY);
        newCompany.setAuthProvider(AuthProvider.LOCAL);

        return companyRepository.save(newCompany);
    }

//    public JobEntity postJob() {
//
//    }

}
