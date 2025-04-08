package com.codelab.rest.webservices.trainingtool.model;

import com.codelab.rest.webservices.trainingtool.annotation.FilterParam;
import com.codelab.rest.webservices.trainingtool.enumerated.CohortType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cohorts", uniqueConstraints = {@UniqueConstraint(columnNames = {"year", "type"})})
public class Cohort extends Identifiable implements Comparable<Cohort> {

    @FilterParam
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CohortType type;

    @FilterParam
    @Column(nullable = false)
    private int year;

    @OneToMany(mappedBy = "cohort", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Team> teams = new ArrayList<>();

    @OneToMany(mappedBy = "cohort")
    private List<User> users = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "program_cohort",
            joinColumns = @JoinColumn(name = "cohort_id"),
            inverseJoinColumns = @JoinColumn(name = "program_id")
    )
    private List<Program> programs = new ArrayList<>();

    public Cohort(CohortType cohortType, int year) {
        this.type = cohortType;
        this.year = year;
    }

    public void addTeam(Team team) {
        team.getCohort().getTeams().remove(team);
        team.setCohort(this);
        teams.add(team);
    }

    public void removeTeam(Team team) {
        if (teams.contains(team)){
            teams.remove(team);
            team.setCohort(this);
        }
    }

    public void addProgram(Program program) {
        if (!programs.contains(program)) {
            program.getCohorts().add(this);
        }
        programs.add(program);
    }

    public void removeProgram(Program program) {
        if (programs.contains(program)){
            program.getCohorts().remove(this);
            programs.remove(program);
        }
    }

    @Override public boolean equals(Object o) {
        if(o instanceof Cohort) {
            return ((Cohort) o).type == type && Objects.equals(((Cohort) o).year, year);
        }
        return false;
    }

    public int compareTo(Cohort cohort) {
        if (year < cohort.year) {
            return -1;
        } else if (year > cohort.year) {
            return 1;
        }

        if (type.compareTo(cohort.type) < 0) {
            return -1;
        } else if (type.compareTo(cohort.type) > 0) {
            return 1;
        }

        return 0;
    }
}