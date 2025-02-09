package br.com.carlos.JobBoard_backend.dto;

import java.util.UUID;

    public record LoggedUserDto(String firstname, String lastName, String email, String password) {
    }
