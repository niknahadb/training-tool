package com.codelab.rest.webservices.trainingtool.controller;

import com.codelab.rest.webservices.trainingtool.payload.LessonProgressDto;
import com.codelab.rest.webservices.trainingtool.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("lesson-progress")
public class LessonProgressController {
    private final LessonProgressService lessonProgressService;

    public LessonProgressController(LessonProgressService lessonProgressService){
        this.lessonProgressService = lessonProgressService;
    }

    @GetMapping("{lessonProgressId}")
    public ResponseEntity<LessonProgressDto> getByLessonProgressId(@PathVariable int lessonProgressId) {
        try {
            LessonProgressDto lessonProgressDTO = lessonProgressService.getLessonProgressById(lessonProgressId);
            return new ResponseEntity<>(lessonProgressDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<List<LessonProgressDto>> getLessonProgresses(
            @RequestParam(required = false) Integer moduleProgressId,
            @RequestParam(required = false) Integer courseProgressId,
            @RequestParam(required = false) Integer lessonId,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Boolean completed) {
        try {
            return new ResponseEntity<>(lessonProgressService
                    .filterLessonProgress(moduleProgressId, courseProgressId, lessonId, userId, completed), HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LessonProgressDto> createLessonProgress(@RequestBody LessonProgressDto lessonProgressDTO) {
        try {
            return new ResponseEntity<>(lessonProgressService.createLessonProgress(lessonProgressDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("{lessonProgressId}/update")
    public ResponseEntity<LessonProgressDto> updateLessonProgress(@RequestBody LessonProgressDto lessonProgressDTO, @PathVariable int lessonProgressId) {
        try {
            return new ResponseEntity<>(lessonProgressService.updateLessonProgressById(lessonProgressId, lessonProgressDTO), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("{lessonProgressId}/delete")
    public ResponseEntity<LessonProgressDto> deleteLessonProgress(@PathVariable int lessonProgressId) {
        try {
            return new ResponseEntity<>(lessonProgressService.deleteLessonProgressById(lessonProgressId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
