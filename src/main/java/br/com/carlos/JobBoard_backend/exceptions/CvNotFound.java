package br.com.carlos.JobBoard_backend.exceptions;

public class CvNotFound extends RuntimeException {
    public CvNotFound() {
        super("Curriculum not found, please upload one");
    }
}
