package com.anurag.CourseService.service;

import com.anurag.CourseService.dto.CourseCount;
import com.anurag.CourseService.dto.CourseRequestDTO;
import com.anurag.CourseService.dto.CourseResponseDTO;
import com.anurag.CourseService.dto.CourseType;
import com.anurag.CourseService.entity.Course;
import com.anurag.CourseService.exception.CourseServiceBusinessException;
import com.anurag.CourseService.exception.ResourceNotFoundException;
import com.anurag.CourseService.repository.CourseRepository;
import com.anurag.CourseService.util.AppUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
@Slf4j
public class CourseService {

    private CourseRepository coursesRepository;

    public CourseResponseDTO addCourse(CourseRequestDTO courseRequestDTO) {
        Course course = AppUtils.mapDtoToEntity(courseRequestDTO);
        Course savedCourse = null;
        log.info("Method : addCourse, execution started.");
        try {
            savedCourse = coursesRepository.save(course);
            log.debug("Method : addCourse, response from database : {}", savedCourse);
            CourseResponseDTO courseResponseDTO = AppUtils.mapEntityToDTO(savedCourse);
            courseResponseDTO.setCourseUniqueCode(UUID.randomUUID().toString());
            log.debug("Method : addCourse, response to controller : {}", AppUtils.convertObjectToString(courseResponseDTO));
            log.info("Method : addCourse, execution ended.");
            return courseResponseDTO;
        } catch (Exception e) {
            log.error("Method : addCourse, exception occured while persisting the course object to DB");
            throw new CourseServiceBusinessException("addCourse method failed.");
        }
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
        try {
            log.debug("Method : deleteCourse, input course id : {}", courseId);
            coursesRepository.deleteById(courseId);
        } catch (Exception ex) {
            log.error("Method : deleteCourse exception occurs while deleting the course object {} ", ex.getMessage());
            throw new CourseServiceBusinessException("deleteCourse method failed");
        }
        log.info("Method : deleteCourse, method execution successful.");
    }

    public CourseResponseDTO updateCourse(int courseId, CourseRequestDTO courseRequestDTO) {
        log.info("Method : updateCourse, method execution started.");

        // get the existing object first
        Course existingCourse = null;
        try {
            log.debug("Method : updateCourse, payload : {}, input course id : {}", AppUtils.convertObjectToString(courseRequestDTO), courseId);
            existingCourse = coursesRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course object not present in DB with id : " + courseId));
            log.debug("Method : updateCourse, existing course fetched from DB : {}", existingCourse);
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
            CourseResponseDTO courseResponseDTO = AppUtils.mapEntityToDTO(updatedCourse);
            log.debug("Method : updateCourse, course response object : {}", courseResponseDTO);
            log.info("Method : updateCourse, method execution ended.");
            return courseResponseDTO;
        } catch (Exception e) {
            log.error("Method : updateCourse, method failed");
            throw new CourseServiceBusinessException("updateCourse method failed");
        }
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
        return allCourses.stream().filter(courseResponseDTO -> courseResponseDTO.getStartDate().before(new Date())).collect(Collectors.toList());
    }

    public List<CourseResponseDTO> futureCourses() {
        List<CourseResponseDTO> allCourses = findAllCourses();
        return allCourses.stream().filter(courseResponseDTO -> courseResponseDTO.getStartDate().after(new Date())).collect(Collectors.toList());
    }

}
