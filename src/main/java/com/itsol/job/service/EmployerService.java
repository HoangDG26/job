package com.itsol.job.service;

import com.itsol.job.enities.Employer;


public interface EmployerService {
    Employer findEmployerById(Long employerId);
}
