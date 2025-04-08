package com.codelab.rest.webservices.trainingtool.service;

import com.codelab.rest.webservices.trainingtool.model.Course;
import com.codelab.rest.webservices.trainingtool.model.CourseCollection;
import com.codelab.rest.webservices.trainingtool.model.Team;
import com.codelab.rest.webservices.trainingtool.payload.CourseCollectionDto;
import com.codelab.rest.webservices.trainingtool.payload.CourseDto;
import com.codelab.rest.webservices.trainingtool.payload.TeamDto;
import com.codelab.rest.webservices.trainingtool.repository.CourseCollectionRepository;
import com.codelab.rest.webservices.trainingtool.repository.CourseRepository;
import com.codelab.rest.webservices.trainingtool.repository.TeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseCollectionService {
    private final IdentifiableService<CourseCollection, CourseCollectionDto> genericService;
    private final IdentifiableService<Course, CourseDto> genericCourseService;
    private final IdentifiableService<Team, TeamDto> genericTeamService;

    public CourseCollectionService(
            CourseCollectionRepository courseCollectionRepository,
            CourseRepository courseRepository,
            TeamRepository teamRepository,
            ModelMapper mapper) {
        this.genericService = new IdentifiableService<>(courseCollectionRepository, mapper, CourseCollection.class, CourseCollectionDto.class);
        this.genericCourseService = new IdentifiableService<>(courseRepository, mapper, Course.class, CourseDto.class);
        this.genericTeamService = new IdentifiableService<>(teamRepository, mapper, Team.class, TeamDto.class);
    }

    public List<CourseCollectionDto> getAllCourseCollection() {
        return genericService.getAll();
    }

    public CourseCollectionDto getCourseCollectionById(int id) {
        return genericService.findById(id);
    }

    public CourseCollectionDto createCourseCollection(CourseCollectionDto courseCollectionDto) {
        CourseCollection courseCollection = new CourseCollection(courseCollectionDto.getName());
        return genericService.save(courseCollection);
    }

    public CourseCollectionDto updateCourseCollectionById(int id, CourseCollectionDto patch) throws IllegalAccessException {
        CourseCollection courseCollection = new CourseCollection(patch.getName());
        return genericService.updateByIdAndSave(id, courseCollection);
    }

    public CourseCollectionDto deleteCourseCollectionById(int id) {
        CourseCollection courseCollection = genericService.findEntityById(id);
        for (Course course : courseCollection.getCourses()) {
            course.getCourseCollections().remove(courseCollection);
        }
        courseCollection.setCourses(new ArrayList<>());
        return genericService.deleteById(id);
    }

    public CourseCollectionDto addCourse(int collectionId, int courseId) {
        CourseCollection courseCollection = genericService.findEntityById(collectionId);
        Course course = genericCourseService.findEntityById(courseId);
        courseCollection.addCourse(course);
        return genericService.save(courseCollection);
    }
}
