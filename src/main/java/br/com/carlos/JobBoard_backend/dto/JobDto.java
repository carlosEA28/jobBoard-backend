package br.com.carlos.JobBoard_backend.dto;

public record JobDto(
        String phoneNumber,
        Integer employeesNumber,
        String jobTitle,
        String jobCategory,
        String jobType,
        String location,
        String employmentType,
        String addressLine,
        Integer salaryRange,
        String salaryBased,
        String aboutCompany,
        String requirements,
        String logoId
) {
}