package com.codelab.rest.webservices.trainingtool.controller;

import com.codelab.rest.webservices.trainingtool.enumerated.MemberRole;
import com.codelab.rest.webservices.trainingtool.model.User;
import com.codelab.rest.webservices.trainingtool.payload.UserDto;
import com.codelab.rest.webservices.trainingtool.service.IdentifiableService;
import com.codelab.rest.webservices.trainingtool.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;}

    @GetMapping()
    public ResponseEntity<List<UserDto>> getUsers(@RequestParam(required = false) MemberRole role,
                                                  @RequestParam(required = false) Integer cohortId,
                                                  @RequestParam(required = false) Integer programId,
                                                  @RequestParam(required = false) Integer teamId) {
        try {
            return new ResponseEntity<>(userService.filterUsers(role, cohortId, programId, teamId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") int userId) {
        try {
            UserDto userResponse = userService.getUserById(userId);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email) {
        try {
            UserDto userResponse = userService.getUserByEmail(email);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        try {
            return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("assign-user/{userId}/to-team-with-id/{teamId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDto> assignToTeam(@PathVariable("userId") int userId,
                                                @PathVariable("teamId") int teamId) {
        try {
            return new ResponseEntity<>(userService.assignToTeam(userId, teamId), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("update-by-id/{id}")
    public ResponseEntity<UserDto> updateUserById(@RequestBody UserDto user, @PathVariable("id") int userId) {
        try {
            return new ResponseEntity<>(userService.updateUserById(userId, user), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("update-by-email/{email}")
    public ResponseEntity<UserDto> updateUserByEmail(@RequestBody UserDto user, @PathVariable("email") String email) {
        try {
            return new ResponseEntity<>(userService.updateUserByEmail(email, user), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("delete-by-id/{id}")
    public ResponseEntity<UserDto> deleteUserById(@PathVariable("id") int userId) {
        try {
            return new ResponseEntity<>(userService.deleteUserById(userId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("delete-by-email/{email}")
    public ResponseEntity<UserDto> deleteUserByEmail(@PathVariable("email") String email) {
        try {
            return new ResponseEntity<>(userService.deleteUserByEmail(email), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("{userId}/remove-from-team")
    public ResponseEntity<UserDto> deleteUsersByTeamId(@PathVariable int userId) {
        try {
            return new ResponseEntity<>(userService.removeFromTeam(userId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
