package com.itsol.job.controller;

import com.itsol.job.dto.request.JobRequest;
import com.itsol.job.dto.response.JobResponse;
import com.itsol.job.service.JobService;
import com.itsol.job.util.SimplePage;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
@Tag(name = "Jobs Controller", description = "Description")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("jobs")
    public ResponseEntity<JobResponse> createNewJob(@RequestBody @Valid JobRequest jobRequest) {
        return jobService.createJob(jobRequest);
    }

    @GetMapping("jobs")
    public ResponseEntity<List<JobResponse>> jobList() {
        return jobService.getJobList();
    }

    @GetMapping("jobs-pagination")
    public ResponseEntity<SimplePage<JobResponse>> jobListWithPagination(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return jobService.getJobs(pageNo, pageSize);
    }

    @GetMapping("jobs/{id}")
    public ResponseEntity<JobResponse> jobDetail(@PathVariable Long id) {
        return jobService.getJob(id);
    }

    @DeleteMapping("jobs/{id}")
    public ResponseEntity<JobResponse> deleteJob(@PathVariable Long id) {
        return jobService.deleteJob(id);
    }

}
