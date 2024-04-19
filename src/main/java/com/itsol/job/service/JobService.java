package com.itsol.job.service;

import com.itsol.job.dto.request.JobRequest;
import com.itsol.job.dto.response.JobResponse;
import com.itsol.job.enities.Job;
import com.itsol.job.util.SimplePage;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface JobService {
    ResponseEntity<JobResponse> createJob(JobRequest jobRequest);

    ResponseEntity<List<JobResponse>> getJobList();

    ResponseEntity<SimplePage<JobResponse>> getJobs(Integer pageNo, Integer pageSize);

    ResponseEntity<List<JobResponse>> getByTitle(String title);

    ResponseEntity<SimplePage<JobResponse>> getJobsWithFilter(String text, Integer pageNo, Integer pageSize);

    ResponseEntity<List<JobResponse>> findJobsWithCursor(String searchTerm, Long cursor, Integer limit);

    ResponseEntity<JobResponse> getJob(Long id);

    ResponseEntity<JobResponse> updateJob(JobRequest jobRequest, Long id);

    ResponseEntity<JobResponse> deleteJob(Long id);
}
