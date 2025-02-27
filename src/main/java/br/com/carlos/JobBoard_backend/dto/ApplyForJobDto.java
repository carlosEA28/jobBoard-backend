package br.com.carlos.JobBoard_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApplyForJobDto(MultipartFile file) {
}
