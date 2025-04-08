package com.codelab.rest.webservices.trainingtool.service;

import com.codelab.rest.webservices.trainingtool.exception.ResourceNotFoundException;
import com.codelab.rest.webservices.trainingtool.model.Course;
import com.codelab.rest.webservices.trainingtool.model.Module;
import com.codelab.rest.webservices.trainingtool.payload.CourseDto;
import com.codelab.rest.webservices.trainingtool.repository.ModuleRepository;
import com.codelab.rest.webservices.trainingtool.repository.CourseRepository;
import com.codelab.rest.webservices.trainingtool.payload.ModuleDto;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class ModuleService {
    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper mapper;
    private final IdentifiableService<Module, ModuleDto> genericService;
    private final IdentifiableService<Course, CourseDto> genericCourseService;

    public ModuleService(ModuleRepository moduleRepository, CourseRepository courseRepository, LessonService lessonService, ModelMapper mapper) {
        this.moduleRepository = moduleRepository;
        this.courseRepository = courseRepository;
        this.mapper = mapper;
        this.genericService = new IdentifiableService<>(moduleRepository, mapper, Module.class, ModuleDto.class);
        this.genericCourseService = new IdentifiableService<>(courseRepository, mapper, Course.class, CourseDto.class);
    }

    public ModuleDto getModuleById(int moduleId) {
        return genericService.findById(moduleId);
    }

    public List<ModuleDto> getAllModules() {
        return genericService.getAll();
    }

    public List<ModuleDto> getModulesByCourseId(int courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new ResourceNotFoundException("Course", "id", courseId)
        );
        List<Module> modules = moduleRepository.findByCourse(course);

        return modules.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ModuleDto createModule(ModuleDto moduleDto) {
        Course course = genericCourseService.findEntityById(moduleDto.getCourseId());
        Module module = new Module(moduleDto.getName(), moduleDto.getDescription(), course);
        return genericService.save(module);
    }

    public ModuleDto updateModuleById(int moduleId, ModuleDto moduleDto) throws IllegalAccessException {
        // TODO make a copy of the module, change the course, save it, and then delete the old module
        Module oldModule = genericService.findEntityById(moduleId);
        Course course = oldModule.getCourse();
        if (genericCourseService.checkReferenceId(moduleDto.getCourseId()) != null) {
            course = genericCourseService.findEntityById(moduleDto.getCourseId());
        }
        Module module = new Module(moduleDto.getName(), moduleDto.getDescription(), course);
        Module updatedModule = genericService.updateById(moduleId, module);
        return genericService.mapToDto(updatedModule);
    }

    public ModuleDto deleteModuleById(int moduleId) {
        return genericService.deleteById(moduleId);
    }

    private ModuleDto mapToDto(Module module){
        return mapper.map(module, ModuleDto.class);
    }
}