package br.com.carlos.JobBoard_backend.exceptions;

public class CompanyAlreadyExists extends RuntimeException {
  public CompanyAlreadyExists(String message) {
    super(message);
  }
}
