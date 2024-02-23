package com.anurag.CourseService.repository;

import com.anurag.CourseService.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Integer> {
}
