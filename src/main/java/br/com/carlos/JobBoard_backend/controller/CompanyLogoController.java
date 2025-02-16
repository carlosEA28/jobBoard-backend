package br.com.carlos.JobBoard_backend.controller;

import br.com.carlos.JobBoard_backend.service.CompanyLogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyLogoController {

    @Autowired
    private CompanyLogoService companyLogoService;

    @GetMapping("/generate")
    public String generatePresignedUrl(@RequestParam String objectKey) {
        return companyLogoService.generatePreSignedUrl(objectKey);
    }
}
