package com.codelab.rest.webservices.trainingtool.service;

import com.codelab.rest.webservices.trainingtool.exception.ResourceNotFoundException;
import com.codelab.rest.webservices.trainingtool.model.Program;
import com.codelab.rest.webservices.trainingtool.payload.ProgramDto;
import com.codelab.rest.webservices.trainingtool.repository.ProgramRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ProgramServiceTest {

    @InjectMocks
    private ProgramService programService;

    @Mock
    private ProgramRepository programRepository;

    @Test
    void getAllProgramsSuccess() {
        List<Program> programs = new ArrayList<>();
        Program program = new Program();
        program.setProgramID(1);
        program.setName("Test Program");
        programs.add(program);

        given(programRepository.findAll()).willReturn(programs);
        List<ProgramDto> result = programService.getAllPrograms();
        assertThat(result).isNotEmpty();
    }

    @Test
    void getProgramByIdSuccess() {
        Program program = new Program();
        program.setProgramID(1);
        program.setName("Test Program");

        given(programRepository.findById(1)).willReturn(Optional.of(program));
        ProgramDto result = programService.getProgramById(1);
        assertThat(result).isNotNull();
        assertThat(result.getProgramID()).isEqualTo(program.getProgramID());
    }

    @Test
    void getProgramByIdFailure() {
        given(programRepository.findById(1)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> programService.getProgramById(1));
    }

    @Test
    void getProgramsByCohortIdSuccess() {
        List<Program> programs = new ArrayList<>();
        Program program = new Program();
        program.setProgramID(1);
        program.setName("Test Program");
        programs.add(program);

        given(programRepository.findByCohortsCohortID(1)).willReturn(programs);
        List<ProgramDto> result = programService.getProgramsByCohortId(1);
        assertThat(result).isNotEmpty();
    }

//    @Test
//    void createProgramSuccess() {
//        ProgramDto programDTO = new ProgramDto(1, "Test Program", true);
//        Program program = new Program();
//        program.setProgramID(1);
//        program.setName("Test Program");
//
//        given(programRepository.save(any(Program.class))).willReturn(program);
//        ProgramDto result = programService.createProgram(programDTO);
//        assertThat(result).isNotNull();
//    }

//    @Test
//    void updateProgramByIdSuccess() {
//        Program program = new Program();
//        program.setProgramID(1);
//        program.setName("Test Program");
//
//        given(programRepository.findById(1)).willReturn(Optional.of(program));
//        ProgramDto newProgramDTO = new ProgramDto(1, "Updated Program", true);
//
//        given(programRepository.save(any(Program.class))).willReturn(program);
//        ProgramDto result = programService.updateProgramById(1, newProgramDTO);
//        assertThat(result).isNotNull();
//    }
//
//    @Test
//    void updateProgramByIdFailure() {
//        given(programRepository.findById(1)).willReturn(Optional.empty());
//        ProgramDto newProgramDTO = new ProgramDto(1, "Updated Program", true);
//
//        assertThrows(ResourceNotFoundException.class, () -> programService.updateProgramById(1, newProgramDTO));
//    }

    @Test
    void deleteProgramByIdSuccess() {
        Program program = new Program();
        program.setProgramID(1);
        program.setName("Test Program");

        given(programRepository.findById(1)).willReturn(Optional.of(program));
        ProgramDto result = programService.deleteProgramById(1);
        assertThat(result).isNotNull();
    }

    @Test
    void deleteProgramByIdFailure() {
        given(programRepository.findById(1)).willReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> programService.deleteProgramById(1));
    }
}
