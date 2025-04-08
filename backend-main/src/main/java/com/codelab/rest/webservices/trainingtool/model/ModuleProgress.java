package com.codelab.rest.webservices.trainingtool.model;

import com.codelab.rest.webservices.trainingtool.annotation.FilterParam;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "module_progress", uniqueConstraints = {@UniqueConstraint(columnNames = {"module_id", "user_id"})})
public class ModuleProgress extends Identifiable {
    @OneToMany(mappedBy = "moduleProgress", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LessonProgress> lessonProgress;

    @FilterParam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="module_id", nullable = false)
    private Module module;

    @FilterParam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_progress_id", nullable = false)
    private CourseProgress courseProgress;

    @FilterParam
    @Setter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public ModuleProgress(Module module, CourseProgress courseProgress) {
        this.module = module;
        this.courseProgress = courseProgress;
        this.user = courseProgress.getUser();
        module.getModuleProgresses().add(this);
        courseProgress.getModuleProgresses().add(this);
        courseProgress.getUser().getModuleProgresses().add(this);
    }

    public void setModule(Module module) {
        this.module.getModuleProgresses().remove(this);
        module.getModuleProgresses().add(this);
        this.module = module;
    }

    public void setCourseProgress(CourseProgress courseProgress) {
        this.courseProgress.getModuleProgresses().remove(this);
        courseProgress.getModuleProgresses().add(this);
        this.courseProgress = courseProgress;
    }

    public void setModuleForFilter(Module module) {
        this.module = module;
    }

    public void setCourseProgressForFilter(CourseProgress courseProgress) {
        this.courseProgress = courseProgress;
    }

    public void setUserForFilter(User user) {
        this.user = user;
    }
}
