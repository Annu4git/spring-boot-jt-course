package com.anurag.CourseService.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDTO {

    private long courseId;

    @NotBlank
    private String courseName;

    @NotNull(message = "this field should be provided")
    private String trainer;

    @NotNull
    private CourseType courseType;

    private LocalDate startDate;

    private String duration;

    @Max(6000)
    private double fees;

    private boolean isCertificationAvailable;

    @NotEmpty(message = "description must not be empty")
    private String description;
}
