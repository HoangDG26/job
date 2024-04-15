package com.itsol.job.enities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Collection;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jobs")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Job extends BaseEntity {
    @Column
    String title;
    @Column(columnDefinition = "TEXT")
    String description;
    @Column
    String location;
    @Column(length = 20)
    String status;
    @Column(columnDefinition = "TEXT")
    String benefit;
    @Column(columnDefinition = "TEXT")
    String requirement;
    String type;
    Integer minSalary;
    Integer maxSalary;
    Integer recruitNum;
    @Column
    String position;
    Integer minYoe;
    Integer maxYoe;
    @Column
    String gender;
    @Column
    LocalDate deadline;
    @ManyToOne
    @JoinColumn(name = "employer_id")
    @JsonIgnore
    Employer employer;
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Collection<JobCategory> jobCategories;
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Collection<JobSkill> jobSkills;
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Collection<JobReport> jobReports;

}
