package com.codelab.rest.webservices.trainingtool.controller;

import com.codelab.rest.webservices.trainingtool.service.CourseProgressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codelab.rest.webservices.trainingtool.payload.CourseProgressDto;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
@RestController
@RequestMapping("course-progress")
public class CourseProgressController {

    private final CourseProgressService courseProgressService;

    public CourseProgressController(CourseProgressService courseProgressService){
        this.courseProgressService = courseProgressService;
    }

    @GetMapping("{courseProgressId}")
    public ResponseEntity<CourseProgressDto> getByCourseProgressId(@PathVariable("courseProgressId") Integer id) {
        try {
            CourseProgressDto courseProgressDTO = courseProgressService.getCourseProgressById(id);
            return new ResponseEntity<>(courseProgressDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<CourseProgressDto>> getCourseProgresses(@RequestParam(required = false) Integer courseId,
                                                                       @RequestParam(required = false) Integer userId,
                                                                       @RequestParam(required = false) Boolean assigned) {
        try {
            List<CourseProgressDto> courseProgressesDTO = courseProgressService.filterCourseProgress(courseId, userId, assigned);
            return new ResponseEntity<>(courseProgressesDTO, HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CourseProgressDto> createCourseProgress(@RequestBody CourseProgressDto courseProgressDTO) {
        try {
            return new ResponseEntity<>(courseProgressService.createCourseProgress(courseProgressDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("{courseProgressId}/update")
    public ResponseEntity<CourseProgressDto> updateCourseProgress(@RequestBody CourseProgressDto courseProgressDTO, @PathVariable("courseProgressId") int id) {
        try {
            return new ResponseEntity<>(courseProgressService.updateCourseProgressById(id, courseProgressDTO), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());        }
    }

     @DeleteMapping("{courseProgressId}/delete")
    public ResponseEntity<CourseProgressDto> deleteCourseProgress(@PathVariable("courseProgressId") int iD) {
        try {
            return new ResponseEntity<>(courseProgressService.deleteCourseProgressById(iD), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());        }
    }

    @GetMapping("percent/user/{userId}/course/{courseId}")
    public ResponseEntity<Double> getCourseProgressPercent(@PathVariable int userId, @PathVariable int courseId) {
        try {
            return new ResponseEntity<>(courseProgressService.getProgressPercentByUserAndCourse(userId, courseId), HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("{courseProgressId}/course-percent")
    public ResponseEntity<Double> getCourseProgressPercent(@PathVariable int courseProgressId) {
        try {
            return new ResponseEntity<>(courseProgressService.getProgressPercent(courseProgressId), HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("user/{userId}/completed")
    public ResponseEntity<List<CourseProgressDto>> getCompletedCourseProgresses(@PathVariable int userId) {
        try {
            return new ResponseEntity<>(courseProgressService.getCompletedCourseProgresses(userId), HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("user/{userId}/not-started")
    public ResponseEntity<List<CourseProgressDto>> getNotStartedCourseProgresses(@PathVariable int userId) {
        try {
            return new ResponseEntity<>(courseProgressService.getNotStartedCourseProgresses(userId), HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("user/{userId}/in-progress")
    public ResponseEntity<List<CourseProgressDto>> getInProgressCourseProgresses(@PathVariable int userId) {
        try {
            return new ResponseEntity<>(courseProgressService.getInProgressCourseProgresses(userId), HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("team/{teamId}/completed")
    public ResponseEntity<List<CourseProgressDto>> getCompletedCourseProgressesByTeam(@PathVariable int teamId) {
        try {
            return new ResponseEntity<>(courseProgressService.getCompletedCourseProgressesFromTeam(teamId), HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("team/{teamId}/not-started")
    public ResponseEntity<List<CourseProgressDto>> getNotStartedCourseProgressesByTeam(@PathVariable int teamId) {
        try {
            return new ResponseEntity<>(courseProgressService.getNotStartedCourseProgressesFromTeam(teamId), HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("team/{teamId}/in-progress")
    public ResponseEntity<List<CourseProgressDto>> getInProgressCourseProgressesByTeam(@PathVariable int teamId) {
        try {
            return new ResponseEntity<>(courseProgressService.getInProgressCourseProgressesFromTeam(teamId), HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
