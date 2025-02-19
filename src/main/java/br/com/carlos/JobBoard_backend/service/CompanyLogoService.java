package br.com.carlos.JobBoard_backend.service;

import br.com.carlos.JobBoard_backend.entity.CompanyLogoEntity;
import br.com.carlos.JobBoard_backend.repository.CompanyLogoRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Service
public class CompanyLogoService {

    @Autowired
    private CompanyLogoRepository companyLogoRepository;

    @Autowired
    private Cloudinary cloudinary;

    public CompanyLogoEntity uploadLogo(String name, MultipartFile file) {
        try {
            var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "logo"));

            var newLogo = new CompanyLogoEntity();
            newLogo.setLogoName(name);
            newLogo.setUrl(uploadResult.get("url").toString());

            return companyLogoRepository.save(newLogo);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Failed to upload the file");
        }
    }

}