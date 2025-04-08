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
@Table(name = "programs")
public class Program extends Identifiable {

    @Column(nullable = false, name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "programs")
    private List<Cohort> cohorts = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "program")
    private List<Team> teams = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "program")
    private List<User> users = new ArrayList<>();

    public Program(String name) {
        this.name = name;
    }
}
