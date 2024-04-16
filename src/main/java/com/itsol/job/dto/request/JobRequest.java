package com.itsol.job.dto.request;

import com.itsol.job.dto.response.CategoryResponse;
import com.itsol.job.dto.response.JobSkillResponse;
import com.itsol.job.enities.Employer;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobRequest {
    String title;
    String description;
    String location;
    String status;
    String benefit;
    String requirement;
    String type;
    Integer minSalary;
    Integer maxSalary;
    Integer recruitNum;
    String position;
    Integer minYoe;
    Integer maxYoe;
    String gender;
    LocalDate deadline;
    EmployerJobRequest employerJobRequest;
    List<JobSkillResponse> jobSkillsJobSkillResponses;
    List<CategoryRequest> jobCategories;
}
