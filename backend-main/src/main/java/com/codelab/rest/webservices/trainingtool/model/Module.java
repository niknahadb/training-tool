package com.codelab.rest.webservices.trainingtool.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "modules")
public class Module extends Identifiable {

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "description", length = 1048576)
    private String description;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "module")
    private List<ModuleProgress> moduleProgresses = new ArrayList<>();

    public Module(String name, String description, Course course) {
        this.name = name;
        this.description = description;
        this.course = course;
        course.getModules().add(this);
    }

    public void setCourse(Course course) {
        this.course.getModules().remove(this);
        course.getModules().add(this);
        this.course = course;
    }
}