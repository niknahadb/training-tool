package com.codelab.rest.webservices.trainingtool.controller;

import com.codelab.rest.webservices.trainingtool.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.codelab.rest.webservices.trainingtool.payload.LessonDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("{lessonId}")
    public ResponseEntity<LessonDto> getLessonId(@PathVariable int lessonId) {
        try {
            LessonDto lessonDTO = lessonService.getLessonById(lessonId);
            return new ResponseEntity<>(lessonDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<LessonDto>> getLessons(@RequestParam(required = false) Integer courseId) {
        try {
            List<LessonDto> lessons = lessonService.filterLessons(courseId);
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LessonDto> createLesson(@RequestBody LessonDto lessonDTO) {
        try {
            return new ResponseEntity<>(lessonService.createLesson(lessonDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("{lessonId}/update")
    public ResponseEntity<LessonDto> updateLesson(@RequestBody LessonDto lessonDTO,
                                                  @PathVariable int lessonId) {
        try {
            return new ResponseEntity<>(lessonService.updateLessonById(lessonId, lessonDTO), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("{lessonId}/delete")
    public ResponseEntity<LessonDto> deleteLesson(@PathVariable int lessonId) {
        try {
            return new ResponseEntity<>(lessonService.deleteLessonById(lessonId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}