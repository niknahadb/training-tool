package com.codelab.rest.webservices.trainingtool.model;

import com.codelab.rest.webservices.trainingtool.annotation.FilterParam;
import com.codelab.rest.webservices.trainingtool.enumerated.CohortType;
import com.codelab.rest.webservices.trainingtool.enumerated.MemberRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "codelab_members")
public class User extends Identifiable {

    @Column(nullable = false, name="first_name")
    private String firstName;

    @Column(nullable = false, name="last_name")
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @FilterParam
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    @FilterParam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id")
    private Team team;

    @Setter(AccessLevel.NONE)
    @FilterParam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cohort_id")
    private Cohort cohort;

    @Setter(AccessLevel.NONE)
    @FilterParam
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="program_id")
    private Program program;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LessonProgress> lessonProgresses = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModuleProgress> moduleProgresses = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseProgress> courseProgresses = new ArrayList<>();

    public User(String firstName, String lastName, String email, String password, MemberRole role, Team team) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.team = team;
        this.cohort = team.getCohort();
        this.program = team.getProgram();
        team.getUsers().add(this);
        team.getCohort().getUsers().add(this);
        team.getProgram().getUsers().add(this);
    }

    public void setTeam(Team team) {
        if (team == null) {
            if (this.team != null) {
                this.team.getUsers().remove(this);
            }
            this.cohort = null;
            this.program = null;
        } else {
            this.team.getUsers().remove(this);
            team.getUsers().add(this);
            this.team = team;
            this.cohort = team.getCohort();
            this.program = team.getProgram();
        }
    }

    public void setTeamForFilter(Team team) {
        this.team = team;
    }

    public void setCohortForFilter(Cohort cohort) {
        this.cohort = cohort;
    }

    public void setProgramForFilter(Program program) {
        this.program = program;
    }
}