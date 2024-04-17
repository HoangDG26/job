package com.itsol.job.service;

import com.itsol.job.dto.request.JobRequest;
import com.itsol.job.dto.response.JobResponse;
import com.itsol.job.util.SimplePage;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface JobService {
    ResponseEntity<JobResponse> createJob(JobRequest jobRequest);

    ResponseEntity<List<JobResponse>> getJobList();

    ResponseEntity<SimplePage<JobResponse>> getJobs(Integer pageNo, Integer pageSize);

    ResponseEntity<List<JobResponse>> getJobsWithFilter(String texBox);

    ResponseEntity<JobResponse> getJob(Long id);

    ResponseEntity<JobResponse> updateJob(JobRequest jobRequest, Long id);

    ResponseEntity<JobResponse> deleteJob(Long id);
}
