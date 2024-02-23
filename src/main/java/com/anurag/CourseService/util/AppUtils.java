package com.anurag.CourseService.util;

import com.anurag.CourseService.dto.CourseRequestDTO;
import com.anurag.CourseService.dto.CourseResponseDTO;
import com.anurag.CourseService.entity.Course;

public class AppUtils {

    // course DTO to course Entity

    public static Course mapDtoToEntity(CourseRequestDTO courseRequestDTO) {
        Course course = new Course();
        course.setCourseId(courseRequestDTO.getCourseId());
        course.setCourseName(courseRequestDTO.getCourseName());
        course.setTrainer(courseRequestDTO.getTrainer());
        course.setCourseType(courseRequestDTO.getCourseType());
        course.setStartDate(courseRequestDTO.getStartDate());
        course.setDuration(courseRequestDTO.getDuration());
        course.setFees(courseRequestDTO.getFees());
        course.setCertificationAvailable(courseRequestDTO.isCertificationAvailable());
        course.setDescription(courseRequestDTO.getDescription());
        return course;
    }

    public static CourseResponseDTO mapEntityToDTO(Course course) {
        CourseResponseDTO courseResponseDTO = new CourseResponseDTO();
        courseResponseDTO.setCourseId(course.getCourseId());
        courseResponseDTO.setCourseName(course.getCourseName());
        courseResponseDTO.setTrainer(course.getTrainer());
        courseResponseDTO.setCourseType(course.getCourseType());
        courseResponseDTO.setStartDate(course.getStartDate());
        courseResponseDTO.setDuration(course.getDuration());
        courseResponseDTO.setFees(course.getFees());
        courseResponseDTO.setCertificationAvailable(course.isCertificationAvailable());
        courseResponseDTO.setDescription(course.getDescription());
        return courseResponseDTO;
    }
}
