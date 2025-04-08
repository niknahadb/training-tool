package com.codelab.rest.webservices.trainingtool.service;


import com.codelab.rest.webservices.trainingtool.exception.ConstrainedIdException;
import com.codelab.rest.webservices.trainingtool.model.*;
import com.codelab.rest.webservices.trainingtool.model.Module;
import com.codelab.rest.webservices.trainingtool.payload.*;

import com.codelab.rest.webservices.trainingtool.repository.*;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonProgressService {
    private final IdentifiableService<LessonProgress, LessonProgressDto> genericService;
    private final IdentifiableService<ModuleProgress, ModuleProgressDto> genericModuleProgressService;
    private final IdentifiableService<Module, ModuleDto> genericModuleService;
    private final IdentifiableService<CourseProgress, CourseProgressDto> genericCourseProgressService;
    private final IdentifiableService<Lesson, LessonDto> genericLessonService;
    private final IdentifiableService<User, UserDto> genericUserService;

    public LessonProgressService(LessonProgressRepository lessonProgressRepository,
                                 ModuleProgressRepository moduleProgressRepository,
                                 CourseProgressRepository courseProgressRepository,
                                 ModuleRepository moduleRepository,
                                 LessonRepository lessonRepository,
                                 UserRepository userRepository,
                                 ModelMapper mapper) {
        this.genericService = new IdentifiableService<>(lessonProgressRepository, mapper, LessonProgress.class, LessonProgressDto.class);
        this.genericLessonService = new IdentifiableService<>(lessonRepository, mapper, Lesson.class, LessonDto.class);
        this.genericCourseProgressService = new IdentifiableService<>(courseProgressRepository, mapper, CourseProgress.class, CourseProgressDto.class);
        this.genericModuleProgressService = new IdentifiableService<>(moduleProgressRepository, mapper, ModuleProgress.class, ModuleProgressDto.class);
        this.genericModuleService = new IdentifiableService<>(moduleRepository, mapper, Module.class, ModuleDto.class);
        this.genericUserService = new IdentifiableService<>(userRepository, mapper, User.class, UserDto.class);
    }

    public LessonProgressDto getLessonProgressById(int lessonProgressId) {
        return genericService.findById(lessonProgressId);
    }

    public List<LessonProgressDto> filterLessonProgress(
            Integer moduleProgressId, Integer courseProgressId, Integer lessonId, Integer userId, Boolean completed) {
        LessonProgress lessonProgress = new LessonProgress();
        lessonProgress.setCompleted(completed);
        lessonProgress.setLesson(genericLessonService.checkReferenceId(lessonId));
        lessonProgress.setModuleProgress(genericModuleProgressService.checkReferenceId(moduleProgressId));
        lessonProgress.setCourseProgressForFilter(genericCourseProgressService.checkReferenceId(courseProgressId));
        lessonProgress.setUserForFilter(genericUserService.checkReferenceId(userId));
        return genericService.filter(lessonProgress);
    }

    public LessonProgressDto createLessonProgress(LessonProgressDto lessonProgressDto) {
        ModuleProgress moduleProgress = genericModuleProgressService.findEntityById(lessonProgressDto.getModuleProgressId());
        Lesson lesson = genericLessonService.findEntityById(lessonProgressDto.getLessonId());
        LessonProgress lessonProgress = new LessonProgress(lesson, lessonProgressDto.getCompleted(), moduleProgress);
        return genericService.save(lessonProgress);
    }

    public LessonProgressDto updateLessonProgressById(int lessonProgressId, LessonProgressDto newLessonProgressDto) throws IllegalAccessException {
        ModuleProgress moduleProgress = genericModuleProgressService.findEntityById(newLessonProgressDto.getModuleProgressId());
        Lesson lesson = genericLessonService.findEntityById(newLessonProgressDto.getLessonId());
        LessonProgress updatedLessonProgress =
                genericService.updateById(lessonProgressId, new LessonProgress(lesson, newLessonProgressDto.getCompleted(), moduleProgress));
        return genericService.save(updatedLessonProgress);
    }

    public LessonProgressDto deleteLessonProgressById(int lessonId) {
        return genericService.deleteById(lessonId);
    }
}