package com.itsol.job.controller;

import com.itsol.job.dto.response.JobResponse;
import com.itsol.job.service.JobService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@Tag(name = "Jobs Controller", description = "Description")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
//
//    @PostMapping("jobs")
//    public ResponseEntity<JobResponse> createNewJob(@RequestBody @Valid JobRequest jobRequest) {
//        return jobService.createJob(jobRequest);
//    }
//
//    @GetMapping("jobs")
//    public ResponseEntity<List<JobResponse>> jobList() {
//        return jobService.getJobList();
//    }
//
//    @PatchMapping("jobs/{id}")
//    public ResponseEntity<JobResponse> updateJob(@RequestBody @Valid JobRequest jobRequest, @PathVariable long id) {
//        return jobService.updateJob(jobRequest, id);
//
//    }
//
//    @GetMapping("jobs-pagination")
//    public ResponseEntity<SimplePage<JobResponse>> jobListWithPagination(
//            @RequestParam(defaultValue = "0") Integer pageNo,
//            @RequestParam(defaultValue = "10") Integer pageSize) {
//        return jobService.getJobs(pageNo, pageSize);
//    }
//
//    @GetMapping("jobs-pagination/{text}")
//    public ResponseEntity<SimplePage<JobResponse>> getJobsWithFilter(
//            @PathVariable String text,
//            @RequestParam(defaultValue = "0") Integer pageNo,
//            @RequestParam(defaultValue = "10") Integer pageSize
//    ) {
//        return jobService.getJobsWithFilter(text, pageNo, pageSize);
//    }

    @GetMapping("jobs")
    public ResponseEntity<List<JobResponse>> findJobsWithCursor(
            @RequestParam(defaultValue = "0") Long cursor,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam String title) {
        return jobService.findJobsWithCursor(title, cursor, limit);
    }

//    @GetMapping("jobs-search/{text}")
//    public ResponseEntity<List<JobResponse>> getJobsWithFilter(
//            @PathVariable String text
//    ) {
//        return jobService.getByTitle(text);
//    }
//
//    @GetMapping("jobs/{id}")
//    public ResponseEntity<JobResponse> jobDetail(@PathVariable Long id) {
//        return jobService.getJob(id);
//    }
//
//    @DeleteMapping("jobs/{id}")
//    public ResponseEntity<JobResponse> deleteJob(@PathVariable Long id) {
//        return jobService.deleteJob(id);
//    }

}
