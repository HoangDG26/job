package com.itsol.job.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationRequest {
    private Integer page = 0;
    private Integer rowsPerPage = 10;
    private String sortBy = "createdAt";
    private boolean descending = true;
}
