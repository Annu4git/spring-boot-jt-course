package com.anurag.CourseService.service;

import com.anurag.CourseService.dto.CourseCount;
import com.anurag.CourseService.dto.CourseRequestDTO;
import com.anurag.CourseService.dto.CourseResponseDTO;
import com.anurag.CourseService.dto.CourseType;
import com.anurag.CourseService.entity.Course;
import com.anurag.CourseService.exception.ResourceNotFoundException;
import com.anurag.CourseService.repository.CourseRepository;
import com.anurag.CourseService.util.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class CourseService {

    private CourseRepository coursesRepository;

    public CourseResponseDTO addCourse(CourseRequestDTO courseRequestDTO) {
        Course course = AppUtils.mapDtoToEntity(courseRequestDTO);
        Course savedCourse = coursesRepository.save(course);
        CourseResponseDTO courseResponseDTO = AppUtils.mapEntityToDTO(savedCourse);
        courseResponseDTO.setCourseUniqueCode(UUID.randomUUID().toString());
        return courseResponseDTO;
    }

    public List<CourseResponseDTO> addCourses(List<CourseRequestDTO> courseList) {
        List<CourseResponseDTO> courseResponseDTOList = courseList.stream()
                .map(courseRequestDTO -> addCourse(courseRequestDTO))
                .collect(Collectors.toList());
        return courseResponseDTOList;
    }

    public List<CourseResponseDTO> findAllCourses() {
        Iterable<Course> courses = coursesRepository.findAll();
        return StreamSupport.stream(courses.spliterator(), false)
                .map(AppUtils::mapEntityToDTO).collect(Collectors.toList());
    }

    public CourseResponseDTO findByCourseId(int courseId) {
        Course course = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("courseId", String.valueOf(courseId)));
        return AppUtils.mapEntityToDTO(course);
    }

    public void deleteCourse(int courseId) {
        coursesRepository.deleteById(courseId);
    }

    public CourseResponseDTO updateCourse(int courseId, CourseRequestDTO courseRequestDTO) {
        // get the existing object first
        Course existingCourse = coursesRepository.findById(courseId).orElseThrow(() -> new RuntimeException());
        existingCourse.setCourseId(courseRequestDTO.getCourseId());
        existingCourse.setCourseName(courseRequestDTO.getCourseName());
        existingCourse.setTrainer(courseRequestDTO.getTrainer());
        existingCourse.setCourseType(courseRequestDTO.getCourseType());
        existingCourse.setStartDate(courseRequestDTO.getStartDate());
        existingCourse.setDuration(courseRequestDTO.getDuration());
        existingCourse.setFees(courseRequestDTO.getFees());
        existingCourse.setCertificationAvailable(courseRequestDTO.isCertificationAvailable());
        existingCourse.setDescription(courseRequestDTO.getDescription());

        Course updatedCourse = coursesRepository.save(existingCourse);

        return AppUtils.mapEntityToDTO(updatedCourse);
    }

    public List<CourseCount> getCourseCount() {
        List<CourseCount> countResponse = new ArrayList<>();
        countResponse.add(new CourseCount(CourseType.LIVE, 0));
        countResponse.add(new CourseCount(CourseType.RECORDING, 0));

        List<CourseResponseDTO> courses = findAllCourses();

        for(CourseResponseDTO course : courses) {
            if(course.getCourseType().equals(CourseType.LIVE)) {
                countResponse.get(0).setCount(countResponse.get(0).getCount() + 1);
            } else if(course.getCourseType().equals(CourseType.RECORDING)) {
                countResponse.get(1).setCount(countResponse.get(1).getCount() + 1);
            }
        }
        return countResponse;
    }

    public void deleteAllCourse(List<Integer> courseIds) {
        coursesRepository.deleteAllById(courseIds);
    }

    public List<CourseResponseDTO> pastCourses() {
        System.out.println("inside past courses service");
        System.out.println(LocalDate.now());
        List<CourseResponseDTO> allCourses = findAllCourses();
        return allCourses.stream().filter(courseResponseDTO -> courseResponseDTO.getStartDate().isBefore(LocalDate.now())).collect(Collectors.toList());
    }

    public List<CourseResponseDTO> futureCourses() {
        List<CourseResponseDTO> allCourses = findAllCourses();
        return allCourses.stream().filter(courseResponseDTO -> courseResponseDTO.getStartDate().isAfter(LocalDate.now())).collect(Collectors.toList());
    }

}
