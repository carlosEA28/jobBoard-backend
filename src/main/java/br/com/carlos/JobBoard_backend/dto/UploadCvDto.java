package br.com.carlos.JobBoard_backend.dto;

import org.springframework.web.multipart.MultipartFile;

public record UploadCvDto(String userId, String cvId, MultipartFile file) {
}
