package com.codelab.rest.webservices.trainingtool.service;

import com.codelab.rest.webservices.trainingtool.exception.ResourceNotFoundException;
import com.codelab.rest.webservices.trainingtool.model.Program;
import com.codelab.rest.webservices.trainingtool.payload.ProgramDto;
import com.codelab.rest.webservices.trainingtool.repository.CohortRepository;
import com.codelab.rest.webservices.trainingtool.repository.ProgramRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;
    private final IdentifiableService<Program, ProgramDto> genericService;

    public ProgramService(ProgramRepository programRepository, CohortRepository cohortRepository, ModelMapper mapper) {
        this.programRepository = programRepository;
        this.genericService = new IdentifiableService<>(programRepository, mapper, Program.class, ProgramDto.class);
    }

    public List<ProgramDto> getAllPrograms() {
        return genericService.getAll();
    }

    public ProgramDto getProgramById(int id) {
        return genericService.findById(id);
    }

    public ProgramDto createProgram(ProgramDto programDto) {
        Program program = new Program(programDto.getName());
        return genericService.save(program);
    }

    public ProgramDto updateProgramById(int id, ProgramDto patch) throws IllegalAccessException {
        Program program = new Program(patch.getName());
        return genericService.updateByIdAndSave(id, program);
    }

    public ProgramDto deleteProgramById(int id) {
        Program program = this.programRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Program", "id", id)
        );
        if (!program.getTeams().isEmpty()) {
            throw new IllegalStateException("Cannot delete program because it still has teams assigned. Please remove all teams associated with this program to delete.");
        }
        return genericService.deleteById(program.getId());
    }
}
