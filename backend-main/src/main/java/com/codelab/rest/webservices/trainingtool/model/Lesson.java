package com.codelab.rest.webservices.trainingtool.model;

import com.codelab.rest.webservices.trainingtool.annotation.FilterParam;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "lessons")
public class Lesson extends Identifiable {

    @Column
    private String name;

    @Column(length = 33554432)
    private String content;

    @ElementCollection
    private List<String> images = new ArrayList<>();

    @ElementCollection
    private List<String> videos = new ArrayList<>();

    @ElementCollection
    private List<String> text = new ArrayList<>();

    @FilterParam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @Setter(AccessLevel.NONE)
    @FilterParam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "lesson")
    private List<LessonProgress> lessonProgresses = new ArrayList<>();

    public Lesson(String name, String content, Module module) {
        this.name = name;
        this.content = content;
        this.module = module;
        this.course = module.getCourse();
        module.getLessons().add(this);
        module.getCourse().getLessons().add(this);
    }

    public void setModule(Module module) {
        this.module.getLessons().remove(this);
        module.getLessons().add(this);
        this.module = module;
        this.course = module.getCourse();
    }

    public void setModuleForFilter(Module module) {
        this.module = module;
    }

    public void setCourseForFilter(Course course) {
        this.course = course;
    }
}