
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
public class ModuleProgressService {
    private final IdentifiableService<ModuleProgress, ModuleProgressDto> genericService;
    private final IdentifiableService<CourseProgress, CourseProgressDto> genericCourseProgressService;
    private final IdentifiableService<Course, CourseDto> genericCourseService;
    private final IdentifiableService<Module, ModuleDto> genericModuleService;
    private final IdentifiableService<User, UserDto> genericUserService;

    public ModuleProgressService(ModuleProgressRepository moduleProgressRepository,
                                 CourseProgressRepository courseProgressRepository,
                                 CourseRepository courseRepository,
                                 ModuleRepository moduleRepository,
                                 UserRepository userRepository,
                                 ModelMapper mapper) {
        this.genericService = new IdentifiableService<>(moduleProgressRepository, mapper, ModuleProgress.class, ModuleProgressDto.class);
        this.genericCourseProgressService = new IdentifiableService<>(courseProgressRepository, mapper, CourseProgress.class, CourseProgressDto.class);
        this.genericCourseService = new IdentifiableService<>(courseRepository, mapper, Course.class, CourseDto.class);
        this.genericModuleService = new IdentifiableService<>(moduleRepository, mapper, Module.class, ModuleDto.class);
        this.genericUserService = new IdentifiableService<>(userRepository, mapper, User.class, UserDto.class);
    }

    public ModuleProgressDto getModuleProgressById(int moduleProgressID) {
        return genericService.findById(moduleProgressID);
    }

    public List<ModuleProgressDto> filterModuleProgress(Integer courseProgressId, Integer moduleId, Integer userId) {
        ModuleProgress moduleProgress = new ModuleProgress();
        moduleProgress.setModule(genericModuleService.checkReferenceId(moduleId));
        moduleProgress.setCourseProgress(genericCourseProgressService.checkReferenceId(courseProgressId));
        moduleProgress.setUserForFilter(genericUserService.checkReferenceId(userId));
        return genericService.filter(moduleProgress);
    }

    public ModuleProgressDto createModuleProgress(ModuleProgressDto moduleProgressDto) {
        CourseProgress courseProgress = genericCourseProgressService.findEntityById(moduleProgressDto.getCourseProgressId());
        Module module = genericModuleService.findEntityById(moduleProgressDto.getModuleId());
        ModuleProgress moduleProgress = new ModuleProgress(module, courseProgress);
        return genericService.save(moduleProgress);
    }

    public ModuleProgressDto updateModuleProgressById(int moduleProgressId, ModuleProgressDto moduleProgressDto) throws IllegalAccessException {
        // TODO make a copy of the moduleProgress, change the courseProgress, save it, and then delete the old moduleProgress
        CourseProgress courseProgress = genericCourseProgressService.findEntityById(moduleProgressDto.getCourseProgressId());
        Module module = genericModuleService.findEntityById(moduleProgressDto.getModuleId());
        ModuleProgress updatedModuleProgress =
                genericService.updateById(moduleProgressId, new ModuleProgress(module, courseProgress));
        return genericService.save(updatedModuleProgress);
    }

    public ModuleProgressDto deleteModuleProgressById(int moduleProgressId) {
        return genericService.deleteById(moduleProgressId);
    }
}