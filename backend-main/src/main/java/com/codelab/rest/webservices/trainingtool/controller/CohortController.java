package com.codelab.rest.webservices.trainingtool.controller;

import com.codelab.rest.webservices.trainingtool.payload.CohortDto;
import com.codelab.rest.webservices.trainingtool.service.CohortService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("cohorts")
public class CohortController {

    private final CohortService cohortService;

    public CohortController(CohortService cohortService) {
        this.cohortService =  cohortService;
    }

    @GetMapping("{cohortId}")
    public ResponseEntity<CohortDto> getCohortById(@PathVariable("cohortId") int cohortId) {
        try {
            CohortDto cohortDTO = cohortService.getCohortById(cohortId);
            return new ResponseEntity<>(cohortDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CohortDto>> getCohort() {
        try {
            List<CohortDto> cohortDTO = cohortService.getAllCohorts();
            return new ResponseEntity<>(cohortDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("current")
    public ResponseEntity<CohortDto> getCurrentCohort() {
        try {
            CohortDto currentCohort = cohortService.getCurrentCohort();
            return new ResponseEntity<>(currentCohort, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("next-cohort")
    public ResponseEntity<CohortDto> getNextCohort() {
        try {
            CohortDto currentCohort = cohortService.getNextCohortDetails();
            return new ResponseEntity<>(currentCohort, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CohortDto> createCohort(@RequestBody CohortDto cohortDTO) {
        try {
            return new ResponseEntity<>(cohortService.createCohort(cohortDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("{id}/update")
    public ResponseEntity<CohortDto> updateCohort(@RequestBody CohortDto cohortDTO, @PathVariable("id") int cohortId) {
        try {
            return new ResponseEntity<>(cohortService.updateCohortById(cohortId, cohortDTO), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("{cohortId}/add-program/{programId}")
    public ResponseEntity<CohortDto> addProgram(@PathVariable int cohortId, @PathVariable int programId) {
        try {
            return new ResponseEntity<>(cohortService.addProgram(cohortId, programId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("{cohortId}/remove-program/{programId}")
    public ResponseEntity<CohortDto> removeProgram(@PathVariable int cohortId, @PathVariable int programId) {
        try {
            return new ResponseEntity<>(cohortService.removeProgram(cohortId, programId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<CohortDto> deleteCohort(@PathVariable("id") int cohortId) {
        try {
            return new ResponseEntity<>(cohortService.deleteCohortById((cohortId)), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}