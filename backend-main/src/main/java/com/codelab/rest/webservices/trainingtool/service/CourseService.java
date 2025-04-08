package com.codelab.rest.webservices.trainingtool.service;

import com.codelab.rest.webservices.trainingtool.enumerated.MemberRole;
import com.codelab.rest.webservices.trainingtool.model.Course;
import com.codelab.rest.webservices.trainingtool.repository.CourseRepository;
import com.codelab.rest.webservices.trainingtool.payload.CourseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseService {
    private final IdentifiableService<Course, CourseDto> genericService;

    public CourseService(CourseRepository courseRepository, ModelMapper mapper) {
        this.genericService = new IdentifiableService<>(courseRepository, mapper, Course.class, CourseDto.class);
    }

    public CourseDto getCourseById(int id) {
        return genericService.findById(id);
    }

    public List<CourseDto> getAllCoursesByType(MemberRole type) {
        Course queryCourse = new Course();
        queryCourse.setType(type);
        return genericService.filter(queryCourse);
    }

    public CourseDto createCourse(CourseDto courseDto) {
        return genericService.save(new Course(
                courseDto.getName(), courseDto.getDescription(), courseDto.getCoverImage(), courseDto.getType()));
    }

    public CourseDto updateCourseById(int id, CourseDto courseDto) throws IllegalAccessException {
        return genericService.updateByIdAndSave(id, new Course(
                courseDto.getName(), courseDto.getDescription(), courseDto.getCoverImage(), courseDto.getType()));
    }

    public CourseDto deleteCourseById(int id) {
        return genericService.deleteById(id);
    }
}