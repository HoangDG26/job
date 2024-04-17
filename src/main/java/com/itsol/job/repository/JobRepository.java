package com.itsol.job.repository;

import com.itsol.job.enities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("select j from Job j WHERE " +
            "CONCAT(j.title, j.description, j.benefit,j.requirement,j.type,j.position ) LIKE %?1% "
    )
    List<Job> searchJobByText(String text);
}
