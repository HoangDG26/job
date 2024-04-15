package com.itsol.job.service;

import com.itsol.job.enities.Job;
import com.itsol.job.repository.JobRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImp implements JobService {

    private final JobRepository jobRepository;

    public JobServiceImp(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public ResponseEntity<List<Job>> getJobList() {
        List<Job> jobList = jobRepository.findAll();
        if (jobList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List Job is empty !!");
        }
        return ResponseEntity.ok().body(jobList);
    }

    @Override
    public ResponseEntity<Optional<Job>> getJob(int id) {
        return null;
    }
}
