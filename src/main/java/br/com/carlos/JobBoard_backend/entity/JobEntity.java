package br.com.carlos.JobBoard_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID jobId;

    @NotBlank(message = "The field [phoneNumber] cannot be empty")
    private String phoneNumber;

    //    @NotBlank(message = "The field [employeesNumber] cannot be empty")
    private Integer employeesNumber;

    @NotBlank(message = "The field [jobTitle] cannot be empty")
    private String jobTitle;

    @NotBlank(message = "The field [jobCategory] cannot be empty")
    private String jobCategory;

    @NotBlank(message = "The field [jobType] cannot be empty")
    private String jobType;

    @NotBlank(message = "The field [location] cannot be empty")
    private String location;

    @NotBlank(message = "The field [EmploymentType] cannot be empty")
    private String EmploymentType;

    @NotBlank(message = "The field [AddressLine] cannot be empty")
    private String AddressLine;

    private Integer salaryRange;


    public String getSalaryBased() {
        return salaryBased;
    }

    public void setSalaryBased(String salaryBased) {
        this.salaryBased = salaryBased;
    }

    @NotBlank(message = "The field [SalaryBased] cannot be empty")
    private String salaryBased;

    @NotBlank(message = "The field [CompanyLogUrl] cannot be empty")
    private String CompanyLogUrl;

    @NotBlank(message = "The field [AboutCompany] cannot be empty")
    private String AboutCompany;

    @NotBlank(message = "The field [Requirements] cannot be empty")
    private String Requirements;

    @ManyToOne
    @JoinColumn(name = "companyId", nullable = false)
    @JsonIgnoreProperties("jobs")
    private CompanyEntity company;

    @OneToOne
    @JoinColumn(name = "logo_id", referencedColumnName = "logo_id")
    @JsonIgnoreProperties("jobEntity")
    private CompanyLogoEntity companyLogoEntity;


    public CompanyLogoEntity getCompanyLogoEntity() {
        return companyLogoEntity;
    }

    public void setCompanyLogoEntity(CompanyLogoEntity companyLogoEntity) {
        this.companyLogoEntity = companyLogoEntity;
    }

    public UUID getJobId() {
        return jobId;
    }

    public void setJobId(UUID jobId) {
        this.jobId = jobId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(Integer employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmploymentType() {
        return EmploymentType;
    }

    public void setEmploymentType(String employmentType) {
        EmploymentType = employmentType;
    }

    public String getAddressLine() {
        return AddressLine;
    }

    public void setAddressLine(String addressLine) {
        AddressLine = addressLine;
    }

    public Integer getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(Integer salaryRange) {
        this.salaryRange = salaryRange;
    }


    public String getCompanyLogUrl() {
        return CompanyLogUrl;
    }

    public void setCompanyLogUrl(String companyLogUrl) {
        CompanyLogUrl = companyLogUrl;
    }

    public String getAboutCompany() {
        return AboutCompany;
    }

    public void setAboutCompany(String aboutCompany) {
        AboutCompany = aboutCompany;
    }

    public String getRequirements() {
        return Requirements;
    }

    public void setRequirements(String requirements) {
        Requirements = requirements;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }
}
