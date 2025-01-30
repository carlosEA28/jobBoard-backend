package br.com.carlos.JobBoard_backend.dto;

public record LoginResponseDto(String token, Long expiresIn) {
}
