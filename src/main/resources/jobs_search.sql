SELECT * FROM job_recommendation.jobs j
WHERE j.title LIKE CONCAT('%', :searchTerm, '%')
  AND j.id < :cursor
ORDER BY j.id ASC
    LIMIT :siz;
