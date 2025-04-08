package com.codelab.rest.webservices.trainingtool.controller;

import com.codelab.rest.webservices.trainingtool.payload.CourseCollectionDto;
import com.codelab.rest.webservices.trainingtool.service.CourseCollectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("course-collections")
public class CourseCollectionController {
    private final CourseCollectionService courseCollectionService;

    public CourseCollectionController(CourseCollectionService courseCollectionService) { this.courseCollectionService = courseCollectionService; }

    @GetMapping
    public ResponseEntity<List<CourseCollectionDto>> getAllCourseCollections() {
        try {
            return new ResponseEntity<>(courseCollectionService.getAllCourseCollection(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("{courseCollectionId}")
    public ResponseEntity<CourseCollectionDto> getCourseCollection(@PathVariable int courseCollectionId) {
        try {
            CourseCollectionDto courseCollectionDto = courseCollectionService.getCourseCollectionById(courseCollectionId);
            return new ResponseEntity<>(courseCollectionDto, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("create")
    public ResponseEntity<CourseCollectionDto> createCourseCollection(@RequestBody CourseCollectionDto courseCollectionDto) {
        try {
            return new ResponseEntity<>(courseCollectionService.createCourseCollection(courseCollectionDto), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("{courseCollectionId}/update")
    public ResponseEntity<CourseCollectionDto> updateCourseCollection(@PathVariable int courseCollectionId, @RequestBody CourseCollectionDto courseCollectionDto) {
        try {
            return new ResponseEntity<>(courseCollectionService.updateCourseCollectionById(courseCollectionId, courseCollectionDto), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("{courseCollectionId}/add-course/{courseId}")
    public ResponseEntity<CourseCollectionDto> addCourse(@PathVariable int courseCollectionId, @PathVariable int courseId) {
        try {
            return new ResponseEntity<>(courseCollectionService.addCourse(courseCollectionId, courseId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("{courseCollectionId}/delete")
    public ResponseEntity<CourseCollectionDto> deleteCourseCollection(@PathVariable int courseCollectionId) {
        try {
            return new ResponseEntity<>(courseCollectionService.deleteCourseCollectionById(courseCollectionId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
