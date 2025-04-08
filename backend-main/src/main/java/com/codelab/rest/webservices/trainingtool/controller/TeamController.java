package com.codelab.rest.webservices.trainingtool.controller;

import com.codelab.rest.webservices.trainingtool.payload.TeamDto;
import com.codelab.rest.webservices.trainingtool.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) { this.teamService = teamService; }

    @GetMapping
    public ResponseEntity<List<TeamDto>> getTeams(@RequestParam(value = "cohortId", required = false) Integer cohortId,
                                                  @RequestParam(value = "programId", required = false) Integer programId) {
        try {
            List<TeamDto> teams = teamService.getTeamsByCohortAndProgramId(cohortId, programId);
            return new ResponseEntity<>(teams, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("{team-id}")
    public ResponseEntity<TeamDto> getTeam(@PathVariable("team-id") int teamId) {
        try {
            TeamDto teamDTO = teamService.getTeamById(teamId);
            return new ResponseEntity<>(teamDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("create")
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto teamDTO) {
        try {
            return new ResponseEntity<>(teamService.createTeam(teamDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("{team-id}/update")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable("team-id") int teamId, @RequestBody TeamDto teamDTO) {
        try {
            return new ResponseEntity<>(teamService.updateTeamById(teamId, teamDTO), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }    }

    @DeleteMapping("{team-id}/delete")
    public ResponseEntity<TeamDto> deleteTeam(@PathVariable("team-id") int teamId) {
        try {
            return new ResponseEntity<>(teamService.deleteTeamById(teamId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
