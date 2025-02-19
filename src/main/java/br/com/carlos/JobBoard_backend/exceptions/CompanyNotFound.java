package br.com.carlos.JobBoard_backend.exceptions;

public class CompanyNotFound extends RuntimeException {
  public CompanyNotFound(String message) {
    super(message);
  }
}
