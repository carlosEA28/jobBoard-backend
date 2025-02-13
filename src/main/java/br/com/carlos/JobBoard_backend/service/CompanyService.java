package br.com.carlos.JobBoard_backend.service;

import br.com.carlos.JobBoard_backend.entity.CompanyEntity;
import br.com.carlos.JobBoard_backend.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity createCompanyAccount(){

        var companyExists = companyRepository.findByEmail()
    }
}
