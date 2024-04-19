package com.itsol.job.service;

import com.itsol.job.dto.request.JobRequest;
import com.itsol.job.dto.response.JobResponse;
import com.itsol.job.enities.Category;
import com.itsol.job.enities.Employer;
import com.itsol.job.enities.Job;
import com.itsol.job.enities.JobCategory;
import com.itsol.job.mapper.JobMapper;
import com.itsol.job.repository.CategoryRepository;
import com.itsol.job.repository.JobRepository;
import com.itsol.job.util.SimplePage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class JobServiceImp implements JobService {

    private final JobRepository jobRepository;
    private final EmployerService employerService;
    private final CategoryRepository categoryRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public JobServiceImp(JobRepository jobRepository,
                         EmployerService employerService,
                         CategoryRepository categoryRepository) {
        this.jobRepository = jobRepository;
        this.employerService = employerService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<JobResponse> createJob(JobRequest jobRequest) {
        Job job = JobMapper.INSTANCE.fromReqToEntity(jobRequest);
        updateJobAttributes(job, jobRequest);
        Job savedJob = jobRepository.save(job);
        JobResponse jobResponse = JobMapper.INSTANCE.fromEntityToRespWithClean(savedJob);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobResponse);

    }

    @Override
    public ResponseEntity<JobResponse> updateJob(JobRequest jobRequest, Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
        updateJobAttributes(job, jobRequest);
        job.setId(id);
        JobResponse jobResponse = JobMapper.INSTANCE.fromEntityToRespWithClean(jobRepository.save(job));
        return ResponseEntity.ok().body(jobResponse);
    }

    private void updateJobAttributes(Job job, JobRequest jobRequest) {
        // Update job categories
        Collection<JobCategory> jobCategories = jobRequest.getJobCategories().stream()
                .map(categoryId -> {
                    Category category = categoryRepository.findById(categoryId.getId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
                    return new JobCategory(job, category);
                })
                .collect(Collectors.toList());
        job.setJobCategories(jobCategories);

        // Update employer
        Employer employer = employerService.findEmployerById(jobRequest.getEmployerJobRequest().getId());
        job.setEmployer(employer);

        // Update job skills
        job.getJobSkills().forEach(skill -> skill.setJob(job));
    }

    @Override
    public ResponseEntity<List<JobResponse>> getJobList() {
        List<Job> jobList = jobRepository.findAll();
        if (jobList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List Job is empty !!");
        }
        List<JobResponse> responses = jobList.stream().map(JobMapper.INSTANCE::fromEntityToRespWithClean)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(responses);
    }

    @Override
    public ResponseEntity<SimplePage<JobResponse>> getJobs(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").descending());
        Page<Job> jobs = jobRepository.findAll(paging);
        return getSimplePageResponseEntity(paging, jobs);
    }

    @Override
    public ResponseEntity<List<JobResponse>> getByTitle(String text) {
        List<Job> jobList = jobRepository.searchJobByText(text);
        if (jobList == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found");
        }
        List<JobResponse> responses = jobList.stream().map(JobMapper.INSTANCE::fromEntityToRespWithClean)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(responses);
    }

    @Override
    public ResponseEntity<SimplePage<JobResponse>> getJobsWithFilter(String text, Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").ascending());

        Page<Job> jobs;
        if (text == null || text.isBlank()) {
            jobs = jobRepository.findAll(paging);
        } else {
            jobs = new PageImpl<>(jobRepository.searchJobByText(text), paging, jobRepository.count());
        }

        return getSimplePageResponseEntity(paging, jobs);
    }

    @Override
    public ResponseEntity<List<JobResponse>> findJobsWithCursor(String searchTerm, Long cursor, Integer limit) {
        List<Job> jobs = jobRepository.findJobsWithCursor(searchTerm, cursor, limit);
        if (jobs == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found");
        }
        List<JobResponse> responses = jobs.stream().map(JobMapper.INSTANCE::fromEntityToRespWithClean)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(responses);
    }

    private ResponseEntity<SimplePage<JobResponse>> getSimplePageResponseEntity(Pageable paging, Page<Job> jobs) {
        if (jobs.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List Job is empty !!");
        }

        SimplePage<JobResponse> jobResponses = new SimplePage<>(
                jobs.getContent().stream()
                        .map(JobMapper.INSTANCE::fromEntityToRespWithClean)
                        .collect(Collectors.toList()), paging, jobs.getTotalElements()
        );
        return ResponseEntity.ok().body(jobResponses);
    }

    @Override
    public ResponseEntity<JobResponse> getJob(Long id) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        Job job = optionalJob.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
        return ResponseEntity.ok().body(JobMapper.INSTANCE.fromEntityToRespWithClean(job));

    }


    @Override
    @Transactional
    public ResponseEntity<JobResponse> deleteJob(Long id) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        Job job = optionalJob.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
        entityManager.createQuery("DELETE FROM JobCategory jc WHERE jc.job = :job")
                .setParameter("job", job)
                .executeUpdate();
        jobRepository.delete(job);
        return ResponseEntity.ok().build();
    }
}
