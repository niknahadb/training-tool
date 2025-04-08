package com.codelab.rest.webservices.trainingtool.model;

import java.util.ArrayList;
import java.util.List;

import com.codelab.rest.webservices.trainingtool.annotation.FilterParam;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "course_progress", uniqueConstraints = {@UniqueConstraint(columnNames = {"course_id", "user_id"})})
public class CourseProgress extends Identifiable{

    @FilterParam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id", nullable = false)
    private Course course;

    @FilterParam
    @Column(nullable = false)
    private Boolean assigned;

    @FilterParam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "courseProgress", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModuleProgress> moduleProgresses = new ArrayList<>();

    @OneToMany(mappedBy = "courseProgress", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LessonProgress> lessonProgresses = new ArrayList<>();

    public CourseProgress(Course course, Boolean assigned, User user) {
        this.course = course;
        this.assigned = assigned;
        this.user = user;
        user.getCourseProgresses().add(this);
    }

    public void setUser(User user) {
        this.user.getCourseProgresses().remove(this);
        user.getCourseProgresses().add(this);
        this.user = user;
    }
}