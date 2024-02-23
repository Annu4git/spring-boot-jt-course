package com.anurag.CourseService.entity;

import com.anurag.CourseService.dto.CourseType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long courseId;

    private String courseName;

    private String trainer;

    private CourseType courseType;

    private LocalDate startDate;

    private String duration;

    private double fees;

    private boolean isCertificationAvailable;

    private String description;

}
