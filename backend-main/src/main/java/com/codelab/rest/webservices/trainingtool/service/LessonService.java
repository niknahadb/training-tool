package com.codelab.rest.webservices.trainingtool.service;

import com.codelab.rest.webservices.trainingtool.exception.ResourceNotFoundException;
import com.codelab.rest.webservices.trainingtool.model.*;
import com.codelab.rest.webservices.trainingtool.model.Module;
import com.codelab.rest.webservices.trainingtool.payload.*;
import com.codelab.rest.webservices.trainingtool.repository.CourseRepository;
import com.codelab.rest.webservices.trainingtool.repository.LessonRepository;
import com.codelab.rest.webservices.trainingtool.repository.ModuleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;
    private final IdentifiableService<Lesson, LessonDto> genericService;
    private final IdentifiableService<Module, ModuleDto> genericModuleService;
    private final IdentifiableService<Course, CourseDto> genericCourseService;

    public LessonService(LessonRepository lessonRepository,
                         ModuleRepository moduleRepository,
                         CourseRepository courseRepository,
                         ModelMapper mapper) {
        this.lessonRepository = lessonRepository;
        this.moduleRepository = moduleRepository;
        this.genericService = new IdentifiableService<>(lessonRepository, mapper, Lesson.class, LessonDto.class);
        this.genericCourseService = new IdentifiableService<>(courseRepository, mapper, Course.class, CourseDto.class);
        this.genericModuleService = new IdentifiableService<>(moduleRepository, mapper, Module.class, ModuleDto.class);
    }

    public LessonDto getLessonById(int lessonId) {
        return genericService.findById(lessonId);
    }

    public List<LessonDto> filterLessons(Integer courseId) {
        Lesson lesson = new Lesson();
        lesson.setCourseForFilter(genericCourseService.checkReferenceId(courseId));
        return genericService.filter(lesson);
    }

    public LessonDto createLesson(LessonDto lessonDto) {
        Module module = genericModuleService.findEntityById(lessonDto.getModuleId());
        Lesson lesson = new Lesson(lessonDto.getName(), lessonDto.getContent(), module);
        return genericService.save(lesson);
    }

    public LessonDto updateLessonById(int lessonId, LessonDto newLessonDto) throws IllegalAccessException {
        // TODO make a copy of the lesson, change the module, save it, and then delete the old lesson
        Lesson lesson = genericService.findEntityById(lessonId);
        if (genericModuleService.checkReferenceId(newLessonDto.getModuleId()) != null) {
            lesson.setModule(genericModuleService.findEntityById(newLessonDto.getModuleId()));
        }
        lesson.setName(newLessonDto.getName());
        lesson.setContent(newLessonDto.getContent());
        return genericService.save(lesson);
    }

    public LessonDto deleteLessonById(int lessonId) {
        return genericService.deleteById(lessonId);
    }

    public void addImage(int lessonId, String fileName) {
        Lesson lesson = this.lessonRepository.findById(lessonId).orElseThrow(
                () -> new ResourceNotFoundException("Module", "id", lessonId)
        );

        List<String> temp  = lesson.getImages();
        temp.add(fileName);
        lesson.setImages(temp);

        Lesson updatedLesson = lessonRepository.save(lesson);
        genericService.mapToDto(updatedLesson);
    }

    public void deleteImage(int lessonId, String fileName) {
        Lesson lesson = this.lessonRepository.findById(lessonId).orElseThrow(
                () -> new ResourceNotFoundException("Module", "id", lessonId)
        );

        List<String> temp  = lesson.getImages();
        temp.remove(fileName);
        lesson.setImages(temp);

        Lesson updatedLesson = lessonRepository.save(lesson);
        genericService.mapToDto(updatedLesson);
    }

    public void addText(int lessonId, String text) {
        Lesson lesson = this.lessonRepository.findById(lessonId).orElseThrow(
                () -> new ResourceNotFoundException("Module", "id", lessonId)
        );

        List<String> temp  = lesson.getText();
        temp.add(text);
        lesson.setImages(temp);

        Lesson updatedLesson = lessonRepository.save(lesson);
        genericService.mapToDto(updatedLesson);
    }

    public void deleteText(int lessonId, String text) {
        Lesson lesson = this.lessonRepository.findById(lessonId).orElseThrow(
                () -> new ResourceNotFoundException("Module", "id", lessonId)
        );

        List<String> temp  = lesson.getImages();
        temp.remove(text);
        lesson.setImages(temp);

        Lesson updatedLesson = lessonRepository.save(lesson);
        genericService.mapToDto(updatedLesson);
    }

    public void moveToModule(int lessonId, int oldModuleId, int newModuleId) {
        Lesson lesson = this.lessonRepository.findById(lessonId).orElseThrow(
                () -> new ResourceNotFoundException("Lesson", "id", lessonId)
        );
        Module oldModule = moduleRepository.findById(oldModuleId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", oldModuleId));

        Module newModule = moduleRepository.findById(newModuleId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", newModuleId));

        List<Lesson> temp = newModule.getLessons();
        temp.add(lesson);
        newModule.setLessons(temp);

        List<Lesson> toDelete = newModule.getLessons();
        toDelete.remove(lesson);
        oldModule.setLessons(toDelete);
    }
}