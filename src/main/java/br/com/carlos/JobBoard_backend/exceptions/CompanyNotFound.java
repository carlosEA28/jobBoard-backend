package br.com.carlos.JobBoard_backend.exceptions;

public class CompanyNotFound extends RuntimeException {
    public CompanyNotFound() {
        super("Company not found");
    }
}
