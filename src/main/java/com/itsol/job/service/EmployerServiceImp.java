package com.itsol.job.service;

import com.itsol.job.enities.Employer;
import com.itsol.job.repository.EmployerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployerServiceImp implements EmployerService {
    private final EmployerRepository employerRepository;

    public EmployerServiceImp(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    @Override
    public Employer findEmployerById(Long employerId) {
        return employerRepository.findById(employerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employer not found"));
    }
}
