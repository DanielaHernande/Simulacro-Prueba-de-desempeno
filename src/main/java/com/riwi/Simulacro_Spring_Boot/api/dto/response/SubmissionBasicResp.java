package com.riwi.Simulacro_Spring_Boot.api.dto.response;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionBasicResp {
    
    private Long id;
    private String content;
    private Date submissionDate;
    private BigDecimal grade;
}
