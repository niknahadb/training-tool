package com.codelab.rest.webservices.trainingtool.controller;

import com.codelab.rest.webservices.trainingtool.enumerated.MemberRole;
import com.codelab.rest.webservices.trainingtool.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codelab.rest.webservices.trainingtool.payload.CourseDto;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("{courseId}")
    public ResponseEntity<CourseDto> getByCourseId(@PathVariable("courseId") int courseId) {
        try {
            CourseDto courseDTO = courseService.getCourseById(courseId);
            return new ResponseEntity<>(courseDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<CourseDto>> getCourses(@RequestParam(required = false, name="type") MemberRole type) {
        try {
            List<CourseDto> courses;
            courses = courseService.getAllCoursesByType(type);
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CourseDto> addCourse(@RequestBody CourseDto courseDTO) {
        try {
            return new ResponseEntity<>(courseService.createCourse(courseDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("{courseId}/update")
    public ResponseEntity<CourseDto> updateCourse(@RequestBody CourseDto courseDTO, @PathVariable("courseId") int courseId) {
        try {
            CourseDto updatedCourse = courseService.updateCourseById(courseId, courseDTO);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("{courseId}/delete")
    public ResponseEntity<CourseDto> deleteCourse(@PathVariable("courseId") int courseId) {
        try {
            return new ResponseEntity<>(courseService.deleteCourseById(courseId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}