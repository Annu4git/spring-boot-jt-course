package com.anurag.CourseService.controller;

import com.anurag.CourseService.dto.CourseRequestDTO;
import com.anurag.CourseService.dto.CourseResponseDTO;
import com.anurag.CourseService.dto.ServiceResponse;
import com.anurag.CourseService.service.CourseService;
import com.anurag.CourseService.util.AppUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/course")
@Slf4j
public class CourseController {


//    Removing property based injection
//    @Autowired
    CourseService courseService;

//    Note: Since @Slf4j annotation is added, then no need to declare "log" variable
//    Logger log = LoggerFactory.getLogger(CourseController.class);

//    including constructor based injection
//    Note : Autowired annotation is not required in this case
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ServiceResponse<CourseResponseDTO> addCourse(@RequestBody @Valid CourseRequestDTO course) {

        log.info("Method : addCourse, request payload : {}", AppUtils.convertObjectToString(course));
        CourseResponseDTO courseResponseDTO = courseService.addCourse(course);
        log.info("Method : addCourse, response : {}", AppUtils.convertObjectToString(courseResponseDTO));
        return new ServiceResponse<>(HttpStatus.CREATED, courseResponseDTO);

    }

    @PostMapping("/addAll")
    public ServiceResponse<?> addAllCourses(@RequestBody @Valid List<CourseRequestDTO> courseList) {
        log.info("Method : addAllCourses, request payload : {}", AppUtils.convertObjectToString(courseList));
        List<CourseResponseDTO> courseResponseDTOList = courseService.addCourses(courseList);
        log.info("Method : addAllCourses, response : {}", AppUtils.convertObjectToString(courseResponseDTOList));
        return new ServiceResponse<>(HttpStatus.OK, courseResponseDTOList);
    }

    @GetMapping
    public ServiceResponse<?> getCourses() {
        List<CourseResponseDTO> allCourses = courseService.findAllCourses();
        log.info("Method : getCourses, response : {}", AppUtils.convertObjectToString(allCourses));
        return new ServiceResponse<>(HttpStatus.OK, allCourses);
    }

    @GetMapping("/{courseId}")
    public ServiceResponse<?> getCourseById(@PathVariable int courseId) {
        log.info("Method : getCourseById, path variable : {}", courseId);
        CourseResponseDTO course = courseService.findByCourseId(courseId);
        log.info("Method : getCourseById, response : {}", course);
        return new ServiceResponse<>(HttpStatus.OK, course);
    }

    @GetMapping("/course")
    public ServiceResponse<?> getCourseByIdRequestParam(@RequestParam(required = false) int courseId) {
        log.info("Method : getCourseByIdRequestParam, request param : {}", courseId);
        CourseResponseDTO course = courseService.findByCourseId(courseId);
        log.info("Method : getCourseByIdRequestParam, response : {}", AppUtils.convertObjectToString(course));
        return new ServiceResponse<>(HttpStatus.OK, course);
    }

    @DeleteMapping("/{courseId}")
    public ServiceResponse<?> deleteCourseById(@PathVariable int courseId) {
        log.info("Method : deleteCourseById, path variable : {}", courseId);
        courseService.deleteCourse(courseId);
        log.info("Method : deleteCourseById, course deleted.");
        return new ServiceResponse<>(HttpStatus.NO_CONTENT, null);
    }

    @DeleteMapping("/all")
    public ServiceResponse<?> deleteAll(@RequestParam List<Integer> courseIds) {
        courseService.deleteAllCourse(courseIds);
        return new ServiceResponse<>(HttpStatus.NO_CONTENT, null);
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCourseCount() {
        return ResponseEntity.ok(courseService.getCourseCount());
    }

    @PutMapping("/{courseId}")
    public ServiceResponse<?> updateCourse(@PathVariable int courseId, @RequestBody @Valid CourseRequestDTO courseRequestDTO) {
        return new ServiceResponse<>(HttpStatus.OK, courseService.updateCourse(courseId, courseRequestDTO));
    }

    @GetMapping("/past")
    public ServiceResponse<?> pastCourses() {
        return new ServiceResponse<>(HttpStatus.OK, courseService.pastCourses());
    }

    @GetMapping("/future")
    public ServiceResponse<?> futureCourses() {
        return new ServiceResponse<>(HttpStatus.OK, courseService.futureCourses());
    }

    @GetMapping("/log")
    public String printLogs() {
        log.error("error msg");
        log.warn("warn msg");
        log.info("info msg");
        log.debug("debug msg");
        log.trace("trace msg");
        return "ABC";
    }
}
