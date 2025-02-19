package br.com.carlos.JobBoard_backend.controller;

import br.com.carlos.JobBoard_backend.entity.CompanyLogoEntity;
import br.com.carlos.JobBoard_backend.service.CompanyLogoService;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/logo")
public class CompanyLogoController {

    @Autowired
    private CompanyLogoService companyLogoService;

    @PostMapping("/upload")
    public ResponseEntity<CompanyLogoEntity> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name) {


        var imageUrl = companyLogoService.uploadLogo(name, file);
        return ResponseEntity.ok(imageUrl);
    }
}
