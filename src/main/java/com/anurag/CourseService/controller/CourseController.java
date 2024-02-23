package com.anurag.CourseService.controller;

import com.anurag.CourseService.dto.CourseRequestDTO;
import com.anurag.CourseService.dto.CourseResponseDTO;
import com.anurag.CourseService.dto.ServiceResponse;
import com.anurag.CourseService.service.CourseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    Logger log = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    CourseService courseService;

    @PostMapping
    public ServiceResponse<CourseResponseDTO> addCourse(@RequestBody @Valid CourseRequestDTO course) {
        return new ServiceResponse<>(HttpStatus.CREATED, courseService.addCourse(course));
    }

    @PostMapping("/addAll")
    public ServiceResponse<?> addAllCourses(@RequestBody @Valid List<CourseRequestDTO> courseList) {
        return new ServiceResponse<>(HttpStatus.OK, courseService.addCourses(courseList));
    }

    @GetMapping
    public ServiceResponse<?> getCourses() {
        return new ServiceResponse<>(HttpStatus.OK, courseService.findAllCourses());
    }

    @GetMapping("/{courseId}")
    public ServiceResponse<?> getCourseById(@PathVariable int courseId) {
        return new ServiceResponse<>(HttpStatus.OK, courseService.findByCourseId(courseId));
    }

    @GetMapping("/course")
    public ServiceResponse<?> getCourseByIdRequestParam(@RequestParam(required = false) int courseId) {
        return new ServiceResponse<>(HttpStatus.OK, courseService.findByCourseId(courseId));
    }

    @DeleteMapping("/{courseId}")
    public ServiceResponse<?> deleteCourseById(@PathVariable int courseId) {
        courseService.deleteCourse(courseId);
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