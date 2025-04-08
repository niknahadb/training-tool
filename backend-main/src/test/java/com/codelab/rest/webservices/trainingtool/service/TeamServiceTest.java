//package com.codelab.rest.webservices.trainingtool.service;
//
//import com.codelab.rest.webservices.trainingtool.exception.ResourceNotFoundException;
//import com.codelab.rest.webservices.trainingtool.model.Cohort;
//import com.codelab.rest.webservices.trainingtool.model.Program;
//import com.codelab.rest.webservices.trainingtool.model.Team;
//import com.codelab.rest.webservices.trainingtool.payload.TeamDto;
//import com.codelab.rest.webservices.trainingtool.repository.TeamRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.BDDMockito.given;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//
//@ExtendWith(MockitoExtension.class)
//public class TeamServiceTest {
//
//    @InjectMocks
//    private TeamService teamService;
//
//    @Mock
//    private TeamRepository teamRepository;
//
//    @Test
//    void getTeamsByCohortAndProgramIdSuccess() {
//        List<Team> teams = new ArrayList<>();
//        Program program = new Program();
//        program.setProgramID(1);
//        Cohort cohort = new Cohort();
//        cohort.setCohortID(1);
//        teams.add(new Team(1, "Team A", new Date(), program, cohort));
//        given(teamRepository.findAll()).willReturn(teams);
//        List<TeamDto> result = teamService.getTeamsByCohortAndProgramId(1, 1);
//        assertThat(result).isNotEmpty();
//        assertThat(result.get(0).getName()).isEqualTo("Team A");
//    }
//
//    @Test
//    void getTeamByIdSuccess() {
//        Team team = new Team(1, "Team A", new Date(), new Program(), new Cohort());
//        given(teamRepository.findById(1)).willReturn(Optional.of(team));
//        TeamDto result = teamService.getTeamById(1);
//        assertThat(result).isNotNull();
//        assertThat(result.getTeamID()).isEqualTo(team.getTeamID());
//    }
//
//    @Test
//    void getTeamByIdFailure() {
//        given(teamRepository.findById(1)).willReturn(Optional.empty());
//        assertThrows(ResourceNotFoundException.class, () -> teamService.getTeamById(1));
//    }
//
//    @Test
//    void createTeamSuccess() {
//        TeamDto teamDTO = new TeamDto(1, "Team A", new Date(), 1, 1);
//        Team team = new Team(1, "Team A", new Date(), new Program(), new Cohort());
//        given(teamRepository.save(any(Team.class))).willReturn(team);
//        TeamDto result = teamService.createTeam(teamDTO);
//        assertThat(result).isNotNull();
//        assertThat(result.getName()).isEqualTo(teamDTO.getName());
//    }
//
////    @Test
////    void updateTeamByIdSuccess() {
////        Team team = new Team(1, "Team A", new Date(), new Program(), new Cohort());
////        given(teamRepository.findById(1)).willReturn(Optional.of(team));
////        TeamDto newTeamDTO = new TeamDto(1, "Team B", new Date(), 1, 1);
////        given(teamRepository.save(team)).willReturn(team);
////        TeamDto result = teamService.updateTeamById(1, newTeamDTO);
////        assertThat(result).isNotNull();
////    }
//
//    @Test
//    void updateTeamByIdFailure() {
//        given(teamRepository.findById(1)).willReturn(Optional.empty());
//        TeamDto newTeamDTO = new TeamDto(1, "Team B", new Date(), 1, 1);
//        assertThrows(ResourceNotFoundException.class, () -> teamService.updateTeamById(1, newTeamDTO));
//    }
//
//    @Test
//    void deleteTeamByIdSuccess() {
//        Team team = new Team(1, "Team A", new Date(), new Program(), new Cohort());
//        given(teamRepository.findById(1)).willReturn(Optional.of(team));
//        TeamDto result = teamService.deleteTeamById(1);
//        assertThat(result).isNotNull();
//    }
//
//    @Test
//    void deleteTeamByIdFailure() {
//        given(teamRepository.findById(1)).willReturn(Optional.empty());
//        assertThrows(ResourceNotFoundException.class, () -> teamService.deleteTeamById(1));
//    }
//}
