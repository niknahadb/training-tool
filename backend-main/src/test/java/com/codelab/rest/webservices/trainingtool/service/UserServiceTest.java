//package com.codelab.rest.webservices.trainingtool.service;
//
//import com.codelab.rest.webservices.trainingtool.exception.ResourceNotFoundExceptionString;
//import com.codelab.rest.webservices.trainingtool.model.User;
//import com.codelab.rest.webservices.trainingtool.payload.UserDto;
//import com.codelab.rest.webservices.trainingtool.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.BDDMockito.given;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceTest {
//
//    @InjectMocks
//    private UserService userService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Nested
//    public class TestClass {
//
//        private UserRepository userRepository;
//        private ModelMapper mapper;
//        private UserService userService;
//        private User testUser;
//
//        @BeforeEach
//        public void setUp() {
//            userRepository = mock(UserRepository.class);
//            userService = new UserService(userRepository, mapper);
//
//            // Define the testUser here
//            // TODO: lets use static classes in the MockUser class instead of defining users here - it is in new mockData folder
//            testUser = new User(1, "Caden", "Newton", "caden@gmail.com", "password", "developer", 1, 1,1);
//        }
//
//        @Test
//        public void GetAllUsersSuccess() {
//            // Define the expected UserResponse based on the testUser
//            UserDto expectedUserResponse = new UserDto(
//                    testUser.getId(),
//                    testUser.getFirstName(),
//                    testUser.getLastName(),
//                    testUser.getEmail(),
//                    testUser.getRole()
//            );
//
//            // Mock the userRepository.findAll() method to return a list containing the testUser
//            List<User> users = new ArrayList<>();
//            users.add(testUser);
//            when(userRepository.findAll()).thenReturn(users);
//
//            // Call the service method to get all users
//            List<UserDto> actualUserResponses = userService.getAllUsers();
//
//            // Assert that the actualUserResponses contains exactly the expectedUserResponse
//            assertThat(actualUserResponses).isEqualTo(expectedUserResponse);
//        }
//
//
//        @Test
//        void testGetUserByID() {
//
//            when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
//
//            UserDto actualUserResponse = userService.getUserById(1);
//
//            UserDto expectedUserResponse = new UserDto(
//                    testUser.getId(),
//                    testUser.getFirstName(),
//                    testUser.getLastName(),
//                    testUser.getEmail(),
//                    testUser.getRole()
//            );
//
//            // Assert that the actualUserResponses contains exactly the addedUserResponse
//            assertThat(actualUserResponse).isEqualTo(expectedUserResponse);
//
//        }
//        @Test
//        void getUserByIDFailure () {
//            given(userRepository.findById(1)).willReturn(Optional.empty());
//            assertThrows(ResourceNotFoundExceptionString.class, () -> userService.getUserById(1));
//        }
//
//        @Test
//        void updateUserByIdSuccess () {
//            User originalUser = new User(1, "Caden", "Newton", "caden@gmail.com", "password", "developer", 1, 1, 1);
//            User newUser = new User(1, "Caden", "Newton", "caden@gmail.com", "password", "developer", 1, 1,1);
//            given(userRepository.findById(1)).willReturn(Optional.of(originalUser));
//            UserDto userResponse = userService.updateUserById(1, newUser);
//            assertThat(userResponse.getFirstName()).isEqualTo(newUser.getFirstName());
//            assertThat(userResponse.getLastName()).isEqualTo(newUser.getLastName());
//        }
//
//        @Test
//        void updateUserByIdFailure() {
//            User newUser = new User(1, "Caden", "Newton", "caden@gmail.com", "password", "developer", 1, 1,1);
//            given(userRepository.findById(1)).willReturn(Optional.empty());
//            assertThrows(ResourceNotFoundExceptionString.class, () -> userService.updateUserById(1, newUser));
//        }
//
//        @Test
//        void createUserTest() {
//            User newUser = new User(1, "Caden", "Newton", "caden@gmail.com", "password", "developer", 1, 1,1);
//            given(userRepository.save(newUser)).willReturn((newUser));
//            assertThat(userService.createUser(newUser)).isEqualTo(newUser);
//        }
//
//        @Test
//        void deleteUserTestSuccess() {
//            User user = new User(1, "Caden", "Newton", "caden@gmail.com", "password", "developer", 1, 1,1);
//            given(userRepository.findById(1)).willReturn(Optional.of(user));
//            assertThat(userService.deleteUserById(1)).isEqualTo(user);
//        }
//
//        @Test
//        void deleteUserTestFailure() {
//            given(userRepository.findById(1)).willReturn(Optional.empty());
//            assertThrows(ResourceNotFoundExceptionString.class, () -> userService.deleteUserById(1));
//        }
//    }
//}
