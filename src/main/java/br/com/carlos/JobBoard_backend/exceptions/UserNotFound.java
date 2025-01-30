package br.com.carlos.JobBoard_backend.exceptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String message) {
        super("User not found");
    }
}
