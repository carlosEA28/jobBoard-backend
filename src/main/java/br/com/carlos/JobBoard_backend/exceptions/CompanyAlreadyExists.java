package br.com.carlos.JobBoard_backend.exceptions;

public class CompanyAlreadyExists extends RuntimeException {
    public CompanyAlreadyExists() {
        super("The company already exists");
    }
}
