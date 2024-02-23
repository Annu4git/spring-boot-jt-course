package com.anurag.CourseService.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseResponseDTO {

    private long courseId;

    private String courseName;

    private String trainer;

    private CourseType courseType;

    private LocalDate startDate;

    private String duration;

    private double fees;

    private boolean isCertificationAvailable;

    private String description;

    private String courseUniqueCode;
}
