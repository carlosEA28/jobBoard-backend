package br.com.carlos.JobBoard_backend.dto;

import jakarta.validation.constraints.Email;

public record UserReedemPasswordDto(@Email String email) {
}
