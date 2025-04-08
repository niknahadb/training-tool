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
@AllArgsConstructor
@Entity
@Table(name = "course_collections")
public class CourseCollection extends Identifiable {

    @Column(nullable = false, name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_collections_courses",
            joinColumns = @JoinColumn(name = "course_collection_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "courseCollection")
    private List<Team> teams = new ArrayList<>();

    public CourseCollection(String name) {
        this.name = name;
    }

    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
            course.getCourseCollections().add(this);
        }
    }
}
