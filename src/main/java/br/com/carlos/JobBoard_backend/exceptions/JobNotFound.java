package br.com.carlos.JobBoard_backend.exceptions;

public class JobNotFound extends RuntimeException {
    public JobNotFound() {
        super("Job not found");
    }
}
