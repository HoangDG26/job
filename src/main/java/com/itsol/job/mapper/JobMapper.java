package com.itsol.job.mapper;


import com.itsol.job.dto.request.JobRequest;
import com.itsol.job.dto.response.CategoryResponse;
import com.itsol.job.dto.response.JobResponse;
import com.itsol.job.dto.response.JobSkillResponse;
import com.itsol.job.enities.Job;
import com.itsol.job.enities.JobSkill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobMapper {
    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    //-----------------------
    @Mappings({
            @Mapping(target = "employer", source = "employer"),
            @Mapping(target = "jobSkills", source = "jobSkills"),
            @Mapping(target = "jobCategories", source = "jobCategories")
    })
    Job fromReqToEntity(JobRequest jobRequest);

    default JobSkill mapJobSkill(JobSkillResponse jobSkillResponse) {
        if (jobSkillResponse == null) {
            return null;
        }
        JobSkill jobSkill = new JobSkill();
        jobSkill.setSkill(jobSkillResponse.getSkill());
        return jobSkill;
    }


    //-----------------------
    JobResponse fromEntityToResp(Job job);

    //----------------------
    default JobResponse fromEntityToRespWithClean(Job job) {
        JobResponse jobResponse = fromEntityToResp(job);
        if (job.getEmployer() != null) {
            jobResponse.setEmployerName(job.getEmployer().getUsername());
        }
        //-------------job skill-----------------------------
        List<JobSkillResponse> jobSkillDTOs = job.getJobSkills().stream()
                .map(skill -> new JobSkillResponse(skill.getSkill()))
                .collect(Collectors.toList());
        jobResponse.setJobSkills(jobSkillDTOs);
        //----------------------category----------
        List<CategoryResponse> jobCategoryDTOs = job.getJobCategories().stream()
                .map(category -> new CategoryResponse(category.getCategory().getDescription()))
                .collect(Collectors.toList());
        jobResponse.setJobCategories(jobCategoryDTOs);
        return jobResponse;
    }
}

