package com.itsol.job.repository;

import com.itsol.job.enities.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {
}
