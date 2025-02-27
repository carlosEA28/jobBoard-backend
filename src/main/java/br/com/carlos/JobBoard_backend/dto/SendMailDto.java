package br.com.carlos.JobBoard_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.web.multipart.MultipartFile;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SendMailDto(String to, String topic, String message, MultipartFile file) {
}
