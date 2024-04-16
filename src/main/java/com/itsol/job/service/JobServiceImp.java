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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImp implements JobService {

    private final JobRepository jobRepository;
    private final EmployerService employerService;
    private final CategoryRepository categoryRepository;

    public JobServiceImp(JobRepository jobRepository, EmployerService employerService, CategoryRepository categoryRepository) {
        this.jobRepository = jobRepository;
        this.employerService = employerService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<JobResponse> createJob(JobRequest jobRequest) {
        Job job = JobMapper.INSTANCE.fromReqToEntity(jobRequest);

        Collection<JobCategory> jobCategories = jobRequest.getJobCategories().stream()
                .map(categoryId -> {
                    Category category = categoryRepository.findById(categoryId.getId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
                    return new JobCategory(job, category);
                })
                .collect(Collectors.toList());
        job.setJobCategories(jobCategories);

        //  Optional<Employer> optionalEmployer = employerRepository.findById(jobRequest.getEmployerJobRequest().getId());
        Employer employer = employerService.findEmployerById(jobRequest.getEmployerJobRequest().getId());
        job.setEmployer(employer);


        job.getJobSkills().forEach(skill -> skill.setJob(job));
        Job savedJob = jobRepository.save(job);

        JobResponse jobResponse = JobMapper.INSTANCE.fromEntityToRespWithClean(savedJob);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobResponse);
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
    public ResponseEntity<JobResponse> updateJob(JobRequest jobRequest, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<JobResponse> deleteJob(Long id) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        Job job = optionalJob.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
        jobRepository.delete(job);
        return ResponseEntity.ok().build();
    }
}
