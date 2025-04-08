package com.codelab.rest.webservices.trainingtool.service;

import com.codelab.rest.webservices.trainingtool.enumerated.MemberRole;
import com.codelab.rest.webservices.trainingtool.exception.ResourceNotFoundException;
import com.codelab.rest.webservices.trainingtool.exception.ResourceNotFoundExceptionString;
import com.codelab.rest.webservices.trainingtool.model.Cohort;
import com.codelab.rest.webservices.trainingtool.model.Program;
import com.codelab.rest.webservices.trainingtool.model.Team;
import com.codelab.rest.webservices.trainingtool.model.User;
import com.codelab.rest.webservices.trainingtool.payload.*;
import com.codelab.rest.webservices.trainingtool.repository.CohortRepository;
import com.codelab.rest.webservices.trainingtool.repository.ProgramRepository;
import com.codelab.rest.webservices.trainingtool.repository.TeamRepository;
import com.codelab.rest.webservices.trainingtool.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final IdentifiableService<User, UserDto> genericService;
    private final IdentifiableService<Cohort, CohortDto> genericCohortService;
    private final IdentifiableService<Program, ProgramDto> genericProgramService;
    private final IdentifiableService<Team, TeamDto> genericTeamService;

    public UserService(UserRepository userRepository,
                       TeamRepository teamRepository,
                       ProgramRepository programRepository,
                       CohortRepository cohortRepository,
                       ModelMapper mapper) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.genericService = new IdentifiableService<>(userRepository, mapper, User.class, UserDto.class);
        this.genericCohortService = new IdentifiableService<>(cohortRepository, mapper, Cohort.class, CohortDto.class);
        this.genericProgramService = new IdentifiableService<>(programRepository, mapper, Program.class, ProgramDto.class);
        this.genericTeamService = new IdentifiableService<>(teamRepository, mapper, Team.class, TeamDto.class);
    }

    public UserDto getUserById(int id) {
        return genericService.findById(id);
    }

    public UserDto updateUserById(int id, UserDto newUser) throws IllegalAccessException {
        Team team = genericTeamService.findEntityById(newUser.getTeamId());
        User user = new User(
                newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), null, newUser.getRole(), team);
        User updatedUser = genericService.updateById(id, user);
        return genericService.save(updatedUser);
    }

    public UserDto deleteUserById(int id) {
        return genericService.deleteById(id);
    }

    public UserDto createUser(UserDto userDto) {
        Team team = genericTeamService.findEntityById(userDto.getTeamId());
        User user = new User(
                userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), null, userDto.getRole(), team);
        return genericService.save(user);
    }

    public UserDto assignToTeam(int userId, int teamId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", teamId));
        user.setTeam(team);
        return genericService.save(user);
    }

    public UserDto getUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundExceptionString("User", "email", email));
        return genericService.mapToDto(user);
    }

    public UserDto updateUserByEmail(String email, UserDto newUser) throws IllegalAccessException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundExceptionString("User", "email", email));
        User updatedUser = genericService.updateById(user.getId(), user);
        return genericService.save(updatedUser);
    }
    public UserDto deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundExceptionString("User", "email", email));
        this.userRepository.delete(user);
        return genericService.mapToDto(user);
    }

    public List<UserDto> filterUsers(MemberRole role, Integer cohortId, Integer programId, Integer teamId) {
        User filter = new User();
        filter.setRole(role);
        filter.setTeamForFilter(genericTeamService.checkReferenceId(teamId));
        filter.setCohortForFilter(genericCohortService.checkReferenceId(cohortId));
        filter.setProgramForFilter(genericProgramService.checkReferenceId(programId));
        return genericService.filter(filter);
    }

    public UserDto removeFromTeam(int userId) throws IllegalAccessException {
        User user = genericService.findEntityById(userId);
        user.setTeam(null);
        return genericService.mapToDto(genericService.updateById(userId, user));
    }
}
