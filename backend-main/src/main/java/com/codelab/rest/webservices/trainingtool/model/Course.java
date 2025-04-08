package com.codelab.rest.webservices.trainingtool.model;

import com.codelab.rest.webservices.trainingtool.annotation.FilterParam;
import com.codelab.rest.webservices.trainingtool.enumerated.MemberRole;
import com.codelab.rest.webservices.trainingtool.repository.CourseRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "courses", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Course extends Identifiable {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 1048576)
    private String description;

    @Column
    private String coverImage;

    @FilterParam
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole type;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Module> modules = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "courses")
    private List<CourseCollection> courseCollections = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<CourseProgress> courseProgresses = new ArrayList<>();

    public Course(String name, String description, String coverImage, MemberRole type) {
        this.name = name;
        this.description = description;
        this.coverImage = coverImage;
        this.type = type;
    }
}