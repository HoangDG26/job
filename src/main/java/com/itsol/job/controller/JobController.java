package com.itsol.job.controller;

import com.itsol.job.enities.Job;
import com.itsol.job.service.JobService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/jobs")
@Tag(name = "Jobs Controller", description = "Description")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    @GetMapping
    public ResponseEntity<List<Job>> jobList(Job job) {
        return jobService.getJobList();
    }

}
