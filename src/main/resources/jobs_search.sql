SELECT j FROM Job j
WHERE j.title LIKE CONCAT('%', :searchTerm, '%')
  AND j.employer.id > :cursor
ORDER BY j.employer.id ASC
    LIMIT :size
