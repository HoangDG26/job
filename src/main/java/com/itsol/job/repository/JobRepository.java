package com.itsol.job.repository;

import com.itsol.job.enities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("select j from Job j WHERE " +
            "j.title LIKE %?1% "
    )
    List<Job> searchJobByText(String text);

    //
    @Query("SELECT j FROM Job j " +
            "WHERE j.title LIKE CONCAT('%', :searchTerm, '%') " +
            "AND j.employer.id > :cursor " +
            "ORDER BY j.employer.id ASC " +
            "LIMIT :size")
    // @Query(value = "resource/jobs_search.sql", nativeQuery = true)
    List<Job> findJobsWithCursor(@Param("searchTerm") String searchTerm,
                                 @Param("cursor") Long cursor,
                                 @Param("size") Integer size);
}

