package com.itsol.job.service;

import com.itsol.job.enities.Job;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface JobService {
    ResponseEntity<List<Job> > getJobList();
    ResponseEntity<Optional<Job>> getJob(int id);
//    Job saveJob(Job job);
}
