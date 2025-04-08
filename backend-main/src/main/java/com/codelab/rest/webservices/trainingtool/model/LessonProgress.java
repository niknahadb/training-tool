package com.codelab.rest.webservices.trainingtool.model;

import com.codelab.rest.webservices.trainingtool.annotation.FilterParam;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "lesson_progress", uniqueConstraints = {@UniqueConstraint(columnNames = {"lesson_id", "user_id"})})
public class LessonProgress extends Identifiable {

    @FilterParam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="lesson_id", nullable = false)
    private Lesson lesson;

    @FilterParam
    @Column(name="completed", nullable = false)
    private Boolean completed;

    @FilterParam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_progress_id", nullable = false)
    private ModuleProgress moduleProgress;

    @Setter(AccessLevel.NONE)
    @FilterParam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Setter(AccessLevel.NONE)
    @FilterParam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_progress_id", nullable = false)
    private CourseProgress courseProgress;

    public LessonProgress(Lesson lesson, Boolean completed, ModuleProgress moduleProgress) {
        this.lesson = lesson;
        this.completed = completed;
        this.moduleProgress = moduleProgress;
        this.user = moduleProgress.getUser();
        this.courseProgress = moduleProgress.getCourseProgress();
        lesson.getLessonProgresses().add(this);
        moduleProgress.getLessonProgress().add(this);
        this.user.getLessonProgresses().add(this);
        this.courseProgress.getLessonProgresses().add(this);
    }

    public void setModuleProgress(ModuleProgress moduleProgress) {
        this.moduleProgress.getLessonProgress().remove(this);
        moduleProgress.getLessonProgress().add(this);
        this.moduleProgress = moduleProgress;
        this.courseProgress = moduleProgress.getCourseProgress();
        this.user = moduleProgress.getUser();
    }

    public void setLesson(Lesson lesson) {
        this.lesson.getLessonProgresses().remove(this);
        lesson.getLessonProgresses().add(this);
        this.lesson = lesson;
    }

    public void setLessonForFilter(Lesson lesson) {
        this.lesson = lesson;
    }

    public void setUserForFilter(User user) {
        this.user = user;
    }

    public void setCourseProgressForFilter(CourseProgress courseProgress) {
        if (moduleProgress == null || moduleProgress.getLessonProgress().equals(courseProgress)) {
            this.courseProgress = courseProgress;
        } else {
            throw new RuntimeException("Cannot set course progress");
        }
    }
}
