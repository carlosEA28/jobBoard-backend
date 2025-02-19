package br.com.carlos.JobBoard_backend.dto;

public record JobDto(String phoneNumber, Integer employeesNumber, String jobTitle, String jobCategory, String jobType,
                     String Location, String EmploymentType, String AddressLine, Integer SalaryRange,
                     String SalaryBased, String CompanyLogUrl, String AboutCompany, String Requirements) {
}
