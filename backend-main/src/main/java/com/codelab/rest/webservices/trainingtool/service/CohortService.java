package com.codelab.rest.webservices.trainingtool.service;

import com.codelab.rest.webservices.trainingtool.enumerated.CohortType;
import com.codelab.rest.webservices.trainingtool.exception.*;
import com.codelab.rest.webservices.trainingtool.model.*;
import com.codelab.rest.webservices.trainingtool.payload.CohortDto;
import com.codelab.rest.webservices.trainingtool.payload.ProgramDto;
import com.codelab.rest.webservices.trainingtool.repository.CohortRepository;
import com.codelab.rest.webservices.trainingtool.repository.ProgramRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.*;

@Service
public class CohortService {

    private final IdentifiableService<Cohort, CohortDto> genericService;
    private final IdentifiableService<Program, ProgramDto> genericProgramService;

    public CohortService(CohortRepository cohortRepository, ProgramRepository programRepository, ModelMapper mapper) {
        this.genericService = new IdentifiableService<>(cohortRepository, mapper, Cohort.class, CohortDto.class);
        this.genericProgramService = new IdentifiableService<>(programRepository, mapper, Program.class, ProgramDto.class);
    }

    public CohortDto getCohortById(int cohortId) {
        return genericService.findById(cohortId);
    }

    public CohortDto getCurrentCohort() {
        List<CohortDto> cohorts = getAllCohorts();
        if (cohorts.isEmpty()) {
            throw new NoResourcesAvailableException("Cohort");
        }
        return cohorts.get(0);
    }

    public CohortDto createCohort(CohortDto cohortDto) {
        Cohort cohort = new Cohort(cohortDto.getType(), cohortDto.getYear());
        List<Program> programs = genericProgramService.getAllEntities();
        cohort.setPrograms(programs);
        for (Program program: programs) {
            program.getCohorts().add(cohort);
            genericProgramService.save(program);
        }
        return genericService.save(cohort);
    }

    public CohortDto addProgram(Integer cohortId, Integer programId) {
        Program program = genericProgramService.findEntityById(programId);
        Cohort cohort = genericService.findEntityById(cohortId);
        cohort.addProgram(program);
        return genericService.save(cohort);
    }

    public CohortDto removeProgram(Integer cohortId, Integer programId) {
        Program program = genericProgramService.findEntityById(programId);

        for (Team team: program.getTeams()) {
            if (team.getProgram().getId().equals(programId)) {
                throw new IllegalStateException("Cannot delete program because it still has teams assigned. Please remove all teams associated with this program to delete.");
            }
        }
        Cohort cohort = genericService.findEntityById(cohortId);
        cohort.removeProgram(program);
        return genericService.save(cohort);
    }

    public CohortDto getNextCohortDetails() {
        Cohort currentCohort = new Cohort();
        try {
            List<Cohort> cohorts = genericService.getAllEntities(Sort.by(Sort.Direction.DESC, "year", "type"));
            if (cohorts.isEmpty()) {
                throw new NoResourcesAvailableException("Cohort");
            }
            currentCohort = cohorts.get(0);
            if (currentCohort.getType().equals(CohortType.FALL)) {
                currentCohort.setType(CohortType.WINTER_SPRING);
                currentCohort.setYear(currentCohort.getYear() + 1);
            } else {
                currentCohort.setType(CohortType.FALL);
            }
        } catch (Exception e){
            currentCohort.setYear(Year.now().getValue());
            currentCohort.setType(CohortType.FALL);
        }
        return genericService.mapToDto(currentCohort);
    }

    public List<CohortDto> getAllCohorts() {
        return genericService.getAll(Sort.by(Sort.Direction.DESC, "year", "type"));
    }

    public CohortDto updateCohortById(int cohortId, CohortDto patch) throws IllegalAccessException {
        Cohort cohort = new Cohort(patch.getType(), patch.getYear());
        return genericService.updateByIdAndSave(cohortId, cohort);
    }

    public CohortDto deleteCohortById(int cohortId) {
        return genericService.deleteById(cohortId);
    }
}
