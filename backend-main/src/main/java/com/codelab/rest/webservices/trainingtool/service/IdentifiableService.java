package com.codelab.rest.webservices.trainingtool.service;

import com.codelab.rest.webservices.trainingtool.annotation.FilterParam;
import com.codelab.rest.webservices.trainingtool.exception.ResourceNotFoundException;
import com.codelab.rest.webservices.trainingtool.model.Identifiable;
import com.codelab.rest.webservices.trainingtool.payload.IdentifiableDto;
import com.codelab.rest.webservices.trainingtool.repository.IdentifiableRepository;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class IdentifiableService<T extends Identifiable, Dto extends IdentifiableDto> {
    private final IdentifiableRepository<T> identifiableRepository;
    private final ModelMapper mapper;
    private final Class<Dto> dtoType;
    private final Class<T> entityType;

    public IdentifiableService(IdentifiableRepository<T> identifiableRepository, ModelMapper mapper, Class<T> entityType, Class<Dto> dtoType) {
        this.identifiableRepository = identifiableRepository;
        this.mapper = mapper;
        this.dtoType = dtoType;
        this.entityType = entityType;
    }

    public Dto save(T entity) {
        T savedEntity = identifiableRepository.save(entity);
        return mapToDto(savedEntity);
    }

    public Dto findById(Integer id) {
        T entity = identifiableRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(entityType.getName(), "id", id)
        );
        return mapToDto(entity);
    }

    public T findEntityById(Integer id) {
        return identifiableRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(entityType.getName(), "id", id)
        );
    }

    public Dto updateByIdAndSave(Integer id, T patchEntity) throws IllegalAccessException {
        return save(updateById(id, patchEntity));
    }

    public T updateById(Integer id, T patchEntity) throws IllegalAccessException {
        T target = identifiableRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(entityType.getName(), "id", id)
        );
        Field[] fields = entityType.getDeclaredFields();
        for(Field field : fields){
            if (!field.isAnnotationPresent(OneToMany.class)
                    && !field.isAnnotationPresent(ManyToMany.class)
                    && !field.isAnnotationPresent(ManyToOne.class)
                    && !field.isAnnotationPresent(OneToOne.class)) {
                field.setAccessible(true);
                Object value = field.get(patchEntity);
                if(value != null){
                    field.set(target, value);
                }
                field.setAccessible(false);
            }
        }
        return target;
    }

    public T checkReferenceId(Integer id) {
        if (id != null) {
            return identifiableRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException(entityType.getName(), "id", id)
            );
        }
        return null;
    }

    public Dto deleteById(Integer id) {
        T entity = identifiableRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(entityType.getName(), "id", id)
        );
        identifiableRepository.delete(entity);
        return mapToDto(entity);
    }

    public List<Dto> getAll() {
        List<T> entities = identifiableRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
        return entities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<Dto> getAll(Sort sort) {
        List<T> entities = identifiableRepository.findAll(sort);
        return entities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<T> getAllEntities() {
        return identifiableRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
    }
    public List<T> getAllEntities(Sort sort) {
        return identifiableRepository.findAll(sort);
    }

    public List<Dto> filter(T item) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher = matcher.withIgnorePaths("id");
        for (Field field : entityType.getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(FilterParam.class)) {
                matcher = matcher.withIgnorePaths(field.getName());
            }
            field.setAccessible(false);
        }
        List<T> entities = identifiableRepository.findAll(Example.of(item, matcher));
        return entities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public  List<Dto> filter(T item, List<String> ignoreParams) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        matcher = matcher.withIgnorePaths("id");
        for (String param : ignoreParams) {
            matcher = matcher.withIgnorePaths(param);
        }
        for (Field field : entityType.getDeclaredFields()) {
            field.setAccessible(true);
            if (!ignoreParams.contains(field.getName())) {
                matcher = matcher.withIgnorePaths(field.getName());
            }
            field.setAccessible(false);
        }
        List<T> entities = identifiableRepository.findAll(Example.of(item, matcher));
        return entities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Dto mapToDto(T course){
        return mapper.map(course, dtoType);
    }
}
