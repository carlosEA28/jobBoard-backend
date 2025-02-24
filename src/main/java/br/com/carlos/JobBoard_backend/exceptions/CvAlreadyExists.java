package br.com.carlos.JobBoard_backend.exceptions;

public class CvAlreadyExists extends RuntimeException {
    public CvAlreadyExists() {
        super("Curriculum already uploaded");
    }
}
