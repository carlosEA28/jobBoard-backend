package br.com.carlos.JobBoard_backend.exceptions;

public class JobAlreadyExists extends RuntimeException {
    public JobAlreadyExists() {
        super("Job already exists");
    }
}
