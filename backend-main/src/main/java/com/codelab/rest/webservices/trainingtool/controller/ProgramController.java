package com.codelab.rest.webservices.trainingtool.controller;

import com.codelab.rest.webservices.trainingtool.model.User;
import com.codelab.rest.webservices.trainingtool.service.ProgramService;
import com.codelab.rest.webservices.trainingtool.payload.ProgramDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("programs")
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) { this.programService = programService; }

    @GetMapping
    public ResponseEntity<List<ProgramDto>> getProgramsByCohort() {
        try {
            return new ResponseEntity<>(programService.getAllPrograms(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("{program-id}")
    public ResponseEntity<ProgramDto> getProgram(@PathVariable("program-id") int programId) {
        try {
            ProgramDto program = programService.getProgramById(programId);
            return new ResponseEntity<>(program, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("create")
    public ResponseEntity<ProgramDto> createProgram(@RequestBody ProgramDto programDTO) {
        try {
            return new ResponseEntity<>(programService.createProgram(programDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("{program-id}/update")
    public ResponseEntity<ProgramDto> updateProgram(@PathVariable("program-id") int programId, @RequestBody ProgramDto programDTO) {
        try {
            return new ResponseEntity<>(programService.updateProgramById(programId, programDTO), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("{program-id}/delete")
    public ResponseEntity<ProgramDto> deleteProgram(@PathVariable("program-id") int programId) {
        try {
            return new ResponseEntity<>(programService.deleteProgramById(programId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
