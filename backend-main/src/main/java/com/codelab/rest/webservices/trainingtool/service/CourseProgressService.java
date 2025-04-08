package com.codelab.rest.webservices.trainingtool.service;


import com.codelab.rest.webservices.trainingtool.exception.ConstrainedIdException;
import com.codelab.rest.webservices.trainingtool.model.*;

import com.codelab.rest.webservices.trainingtool.payload.*;
import com.codelab.rest.webservices.trainingtool.repository.*;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseProgressService {
    private final IdentifiableService<CourseProgress, CourseProgressDto> genericService;
    private final LessonProgressService lessonProgressService;
    private final IdentifiableService<Course, CourseDto> genericCourseService;
    private final IdentifiableService<User, UserDto> genericUserService;
    private final IdentifiableService<Team, TeamDto> genericTeamService;


    public CourseProgressService(CourseProgressRepository courseProgressRepository,
                                 LessonProgressRepository lessonProgressRepository,
                                 UserRepository userRepository,
                                 CourseRepository courseRepository,
                                 ModuleProgressRepository moduleProgressRepository,
                                 ModuleRepository moduleRepository,
                                 LessonRepository lessonRepository,
                                 TeamRepository teamRepository,
                                 ModelMapper mapper) {
        this.genericService = new IdentifiableService<>(courseProgressRepository, mapper, CourseProgress.class, CourseProgressDto.class);
        this.genericUserService = new IdentifiableService<>(userRepository, mapper, User.class, UserDto.class);
        this.genericCourseService = new IdentifiableService<>(courseRepository, mapper, Course.class, CourseDto.class);
        this.genericTeamService = new IdentifiableService<>(teamRepository, mapper, Team.class, TeamDto.class);
        this.lessonProgressService = new LessonProgressService(
                lessonProgressRepository,
                moduleProgressRepository,
                courseProgressRepository,
                moduleRepository,
                lessonRepository,
                userRepository,
                mapper);
    }

    public CourseProgressDto getCourseProgressById(int id) {
        return genericService.findById(id);
    }

    public List<CourseProgressDto> filterCourseProgress(Integer courseId, Integer userId, Boolean assigned) {
        if (courseId == null && userId == null && assigned == null) {
            return genericService.getAll();
        }
        CourseProgress courseProgress = new CourseProgress();
        courseProgress.setCourse(genericCourseService.checkReferenceId(courseId));
        courseProgress.setUser(genericUserService.checkReferenceId(userId));
        courseProgress.setAssigned(assigned);
        return genericService.filter(courseProgress);
    }

    public CourseProgressDto createCourseProgress(CourseProgressDto courseProgressDto) {
        User user = genericUserService.findEntityById(courseProgressDto.getUserId());
        Course course = genericCourseService.findEntityById(courseProgressDto.getCourseId());
        CourseProgress courseProgress = new CourseProgress(course, courseProgressDto.isAssigned(), user);
        return genericService.save(courseProgress);
    }

    public CourseProgressDto deleteCourseProgressById(int id) {
        return genericService.deleteById(id);
    }

    public CourseProgressDto updateCourseProgressById(int id, CourseProgressDto courseProgressDto) throws IllegalAccessException {
        User user = genericUserService.findEntityById(courseProgressDto.getUserId());
        Course course = genericCourseService.findEntityById(courseProgressDto.getCourseId());
        CourseProgress updatedCourseProgress =
                genericService.updateById(id, new CourseProgress(course, courseProgressDto.isAssigned(), user));
        updatedCourseProgress.setUser(user);
        updatedCourseProgress.setCourse(course);
        return genericService.save(updatedCourseProgress);
    }

    private Integer getCourseProgressId(Integer userId, Integer courseId) {
        CourseProgress filter = new CourseProgress();
        filter.setUser(genericUserService.findEntityById(userId));
        filter.setCourse(genericCourseService.findEntityById(courseId));
        List<CourseProgressDto> courseProgressDtos = genericService.filter(filter);
        if (courseProgressDtos.size() == 1) {
            return courseProgressDtos.get(0).getId();
        }
        throw new RuntimeException("Could not find course progress with given userId and courseId");
    }

    public double getProgressPercentByUserAndCourse(Integer userId, Integer courseId){
        Integer courseProgressId = getCourseProgressId(userId, courseId);
        return getProgressPercent(courseProgressId);
    }

    public double getProgressPercent(Integer courseProgressId){
        double total = lessonProgressService.filterLessonProgress(null, courseProgressId, null, null, null).size();
        double completed = lessonProgressService.filterLessonProgress(null, courseProgressId, null, null, true).size();
        if (total == 0) {
            return 0;
        }
        return (completed/total);
    }

    public boolean getIsCourseCompleted(Integer courseProgressId){
        Integer total = lessonProgressService.filterLessonProgress(null, courseProgressId, null, null, null).size();
        Integer completed = lessonProgressService.filterLessonProgress(null, courseProgressId, null, null, true).size();
        if (total == 0) {
            return false;
        }
        return (completed.equals(total));
    }

    public boolean getIsCourseNotStarted(Integer courseProgressId){
        Integer completed = lessonProgressService.filterLessonProgress(null, courseProgressId, null, null, true).size();
        if (completed == 0) {
            return true;
        }
        return false;
    }

    public List<CourseProgressDto> getCompletedCourseProgresses(Integer userId) {
        CourseProgress filter = new CourseProgress();
        filter.setUser(genericUserService.findEntityById(userId));
        List<CourseProgressDto> courseProgressDtos = genericService.filter(filter);
        return courseProgressDtos.stream()
                .filter(courseProgressDto -> getIsCourseCompleted(courseProgressDto.getId()))
                .collect(Collectors.toList());
    }

    public List<CourseProgressDto> getNotStartedCourseProgresses(Integer userId) {
        CourseProgress filter = new CourseProgress();
        filter.setUser(genericUserService.findEntityById(userId));
        List<CourseProgressDto> courseProgressDtos = genericService.filter(filter);
        return courseProgressDtos.stream()
                .filter(courseProgressDto -> getIsCourseNotStarted(courseProgressDto.getId()))
                .collect(Collectors.toList());
    }

    public List<CourseProgressDto> getInProgressCourseProgresses(Integer userId) {
        CourseProgress filter = new CourseProgress();
        filter.setUser(genericUserService.findEntityById(userId));
        List<CourseProgressDto> courseProgressDtos = genericService.filter(filter);
        return courseProgressDtos.stream()
                .filter(courseProgressDto -> !getIsCourseNotStarted(courseProgressDto.getId())
                        && !getIsCourseCompleted(courseProgressDto.getId()))
                .collect(Collectors.toList());
    }

    private List<CourseProgressDto> getCourseProgressesFromTeamId(Integer teamId) {
        User teamFilter = new User();
        teamFilter.setTeam(genericTeamService.findEntityById(teamId));
        List<UserDto> users = genericUserService.filter(teamFilter);
        List<CourseProgressDto> allCourseProgresses = new ArrayList<>();
        for (UserDto user: users) {
            CourseProgress userFilter = new CourseProgress();
            userFilter.setUser(genericUserService.findEntityById(user.getId()));
            List<CourseProgressDto> courseProgressDtos = genericService.filter(userFilter);
            allCourseProgresses.addAll(courseProgressDtos);
        }
        return allCourseProgresses;
    }

    public List<CourseProgressDto> getCompletedCourseProgressesFromTeam(Integer teamId) {
        List<CourseProgressDto> allCourseProgresses = getCourseProgressesFromTeamId(teamId);
        return allCourseProgresses.stream()
                .filter(courseProgressDto -> getIsCourseCompleted(courseProgressDto.getId()))
                .collect(Collectors.toList());
    }

    public List<CourseProgressDto> getNotStartedCourseProgressesFromTeam(Integer teamId) {
        List<CourseProgressDto> allCourseProgresses = getCourseProgressesFromTeamId(teamId);
        return allCourseProgresses.stream()
                .filter(courseProgressDto -> getIsCourseNotStarted(courseProgressDto.getId()))
                .collect(Collectors.toList());
    }

    public List<CourseProgressDto> getInProgressCourseProgressesFromTeam(Integer teamId) {
        List<CourseProgressDto> allCourseProgresses = getCourseProgressesFromTeamId(teamId);
        return allCourseProgresses.stream()
                .filter(courseProgressDto -> !getIsCourseNotStarted(courseProgressDto.getId())
                        && !getIsCourseCompleted(courseProgressDto.getId()))
                .collect(Collectors.toList());
    }
}