package com.codelab.rest.webservices.trainingtool.service;

import com.codelab.rest.webservices.trainingtool.model.*;
import com.codelab.rest.webservices.trainingtool.model.Module;
import com.codelab.rest.webservices.trainingtool.payload.*;
import com.codelab.rest.webservices.trainingtool.repository.CohortRepository;
import com.codelab.rest.webservices.trainingtool.repository.CourseCollectionRepository;
import com.codelab.rest.webservices.trainingtool.repository.TeamRepository;
import com.codelab.rest.webservices.trainingtool.repository.ProgramRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final IdentifiableService<Team, TeamDto> genericService;
    private final IdentifiableService<Cohort, CohortDto> genericCohortService;
    private final IdentifiableService<Program, ProgramDto> genericProgramService;
    private final IdentifiableService<CourseCollection, CourseCollectionDto> genericCourseCollectionService;

    public TeamService(TeamRepository teamRepository,
                       CohortRepository cohortRepository,
                       ProgramRepository programRepository,
                       CourseCollectionRepository courseCollectionRepository,
                       ModelMapper mapper) {
        this.teamRepository = teamRepository;
        this.genericCohortService = new IdentifiableService<>(cohortRepository, mapper, Cohort.class, CohortDto.class);
        this.genericProgramService = new IdentifiableService<>(programRepository, mapper, Program.class, ProgramDto.class);
        this.genericCourseCollectionService = new IdentifiableService<>(courseCollectionRepository, mapper, CourseCollection.class, CourseCollectionDto.class);
        this.genericService = new IdentifiableService<>(teamRepository, mapper, Team.class, TeamDto.class);
    }

    public List<TeamDto> getTeamsByCohortAndProgramId(Integer cohortId, Integer programId) {
        List<Team> teams = teamRepository.findAll();
        if (cohortId != null && programId != null) {
            teams = teamRepository.findByCohortIdAndProgramId(cohortId, programId);
        } else if (cohortId != null) {
            teams = teamRepository.findByCohortId(cohortId);
        } else if (programId != null) {
            teams = teamRepository.findByProgramId(programId);
        }
        return teams.stream().map(genericService::mapToDto).collect(Collectors.toList());
    }

    public TeamDto getTeamById(int id) {
        return genericService.findById(id);
    }

    public TeamDto createTeam(TeamDto teamDto) {
        Program program = genericProgramService.findEntityById(teamDto.getProgramId());
        Cohort cohort = genericCohortService.findEntityById(teamDto.getCohortId());
        CourseCollection courseCollection = genericCourseCollectionService.findEntityById(teamDto.getCourseCollectionId());
        Team team = new Team(teamDto.getName(), program, cohort, courseCollection);
        return genericService.save(team);
    }

    public TeamDto updateTeamById(int teamId, TeamDto patch) throws IllegalAccessException {
        Program program = genericProgramService.findEntityById(patch.getProgramId());
        Cohort cohort = genericCohortService.findEntityById(patch.getCohortId());
        CourseCollection courseCollection = genericCourseCollectionService.findEntityById(patch.getCourseCollectionId());
        Team updatedTeam = genericService.updateById(teamId, new Team(patch.getName(), program, cohort, courseCollection));
        return genericService.save(updatedTeam);
    }

    public TeamDto deleteTeamById(int id) {
        return genericService.deleteById(id);
    }
}
