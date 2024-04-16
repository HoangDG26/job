package com.itsol.job.dto.response;

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
public class JobResponse {
    Long id;
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
    String employerName;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<JobSkillResponse> jobSkills;
    List<CategoryResponse> jobCategories;
//    List<JobReportResponse> jobReports;
}
