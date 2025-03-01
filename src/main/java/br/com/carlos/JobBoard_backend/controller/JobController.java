package br.com.carlos.JobBoard_backend.controller;

import br.com.carlos.JobBoard_backend.dto.JobDto;
import br.com.carlos.JobBoard_backend.dto.UpdateJobDto;
import br.com.carlos.JobBoard_backend.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping("/type/{jobType}")
    public ResponseEntity<List<JobDto>> getAllByJobType(@PathVariable String jobType) {
        var jobs = jobService.getJobByJobType(jobType);
        return ResponseEntity.ok().body(jobs);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<JobDto>> getAllByJobLocation(@PathVariable String location) {
        var jobs = jobService.getJobByJobLocation(location);
        return ResponseEntity.ok().body(jobs);
    }

    @GetMapping("/salary-range/{range}")
    public ResponseEntity<List<JobDto>> getAllBySalaryRange(@PathVariable Integer range) {
        var jobs = jobService.getJobBySalaryRange(range);
        return ResponseEntity.ok().body(jobs);
    }

    @GetMapping("/salary-based/{based}")
    public ResponseEntity<List<JobDto>> getAllBySalaryBased(@PathVariable String based) {
        var jobs = jobService.getJobBySalaryBased(based);
        return ResponseEntity.ok().body(jobs);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<JobDto>> getAllByJobCategory(@PathVariable String category) {
        var jobs = jobService.getJobByJobCategory(category);
        return ResponseEntity.ok().body(jobs);
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<Void> updateJob(@PathVariable String jobId, @RequestBody UpdateJobDto dto) {

        return jobService.updateJob(jobId, dto);
    }

    @DeleteMapping("/delete/{jobId}")
    public ResponseEntity<String> deleteJob(@PathVariable String jobId) {

        jobService.deleteJob(jobId);
        return ResponseEntity.ok().body("Job deleted");
    }
}


