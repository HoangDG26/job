package com.itsol.job.enities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itsol.job.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jobs")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Job extends BaseEntity {
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
    Integer minYeo;
    Integer maxYeo;
    @Enumerated(EnumType.STRING)
    Gender gender;
    LocalDateTime deadline;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    EmployerAccount employerAccount;
    @ManyToMany(mappedBy = "jobs")
    Set<Category> categories;
}
