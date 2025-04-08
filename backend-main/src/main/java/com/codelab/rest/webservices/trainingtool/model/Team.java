package com.codelab.rest.webservices.trainingtool.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teams")
public class Team extends Identifiable {

    @Column(nullable = false, name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cohort_id")
    private Cohort cohort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_collection_id")
    private CourseCollection courseCollection;

    @OneToMany(mappedBy = "team")
    private List<User> users;

    public Team(String name, Program program, Cohort cohort, CourseCollection courseCollection) {
        this.name = name;
        this.program = program;
        this.cohort = cohort;
        this.courseCollection = courseCollection;
        program.getTeams().add(this);
        cohort.getTeams().add(this);
        courseCollection.getTeams().add(this);
    }

    public void setProgram(Program program) {
        this.program.getTeams().remove(this);
        program.getTeams().add(this);
        this.program = program;
    }

    public void setCohort(Cohort cohort) {
        this.cohort.getTeams().remove(this);
        cohort.getTeams().add(this);
        this.cohort = cohort;
    }

    public void setCourseCollection(CourseCollection courseCollection) {
        this.courseCollection.getTeams().remove(this);
        courseCollection.getTeams().add(this);
        this.courseCollection = courseCollection;
    }
}