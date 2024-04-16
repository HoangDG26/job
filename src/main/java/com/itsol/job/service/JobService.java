package com.itsol.job.service;

import com.itsol.job.dto.request.JobRequest;
import com.itsol.job.dto.response.JobResponse;
import com.itsol.job.util.SimplePage;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface JobService {
    ResponseEntity<JobResponse> createJob(JobRequest jobRequest);

    ResponseEntity<List<JobResponse>> getJobList();

    ResponseEntity<SimplePage<JobResponse>> getJobs(Integer pageNo, Integer pageSize);

    ResponseEntity<JobResponse> getJob(Long id);

    ResponseEntity<JobResponse> updateJob(JobRequest jobRequest, Long id);

    ResponseEntity<JobResponse> deleteJob(Long id);
}
