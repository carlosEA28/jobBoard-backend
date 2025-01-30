package br.com.carlos.JobBoard_backend.exceptions;

public class WrongCreadentials extends RuntimeException {
    public WrongCreadentials(String message) {
        super(message);
    }
}
