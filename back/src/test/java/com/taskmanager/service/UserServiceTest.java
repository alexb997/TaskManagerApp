package com.taskmanager.service;

import com.taskmanager.model.User;
import com.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user1 = new User(1L, "user1", "password1");
        user2 = new User(2L, "user2", "password2");
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user1);

        User createdUser = userService.createUser(user1);

        assertNotNull(createdUser, "Created user should not be null");
        assertEquals("user1", createdUser.getUsername(), "User name should match");
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.getAllUsers();

        assertNotNull(users, "User list should not be null");
        assertEquals(2, users.size(), "User list size should match");
        assertTrue(users.contains(user1), "User list should contain user1");
        assertTrue(users.contains(user2), "User list should contain user2");
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        Optional<User> user = userService.getUserById(1L);

        assertTrue(user.isPresent(), "User should be present");
        assertEquals("user1", user.get().getUsername(), "User name should match");
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateUser() {
        User updatedUserDetails = new User(1L, "updatedUser", "updatedPassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.save(any(User.class))).thenReturn(updatedUserDetails);

        User updatedUser = userService.updateUser(1L, updatedUserDetails);

        assertNotNull(updatedUser, "Updated user should not be null");
        assertEquals("updatedUser", updatedUser.getUsername(), "Updated user name should match");
        assertEquals("updatedPassword", updatedUser.getPassword(), "Updated user password should match");
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(updatedUserDetails);
    }

    @Test
    void testUpdateUser_NotFound() {
        User updatedUserDetails = new User(1L, "updatedUser", "updatedPassword");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(1L, updatedUserDetails);
        });

        assertEquals("User not found with id: 1", thrownException.getMessage(), "Exception message should match");
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);

        boolean isDeleted = userService.deleteUser(1L);

        assertTrue(isDeleted, "User should be deleted");
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        boolean isDeleted = userService.deleteUser(1L);

        assertFalse(isDeleted, "User should not be deleted if not found");
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, never()).deleteById(1L);
    }

    @Test
    void testAuthenticateUser_Success() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user1));

        boolean isAuthenticated = userService.authenticateUser("user1", "password1");

        assertTrue(isAuthenticated, "User should be authenticated");
        verify(userRepository, times(1)).findByUsername("user1");
    }

    @Test
    void testAuthenticateUser_Failure() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user1));

        boolean isAuthenticated = userService.authenticateUser("user1", "wrongPassword");

        assertFalse(isAuthenticated, "User should not be authenticated with incorrect password");
        verify(userRepository, times(1)).findByUsername("user1");
    }

    @Test
    void testAuthenticateUser_UserNotFound() {
        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());

        boolean isAuthenticated = userService.authenticateUser("user1", "password1");

        assertFalse(isAuthenticated, "User should not be authenticated if not found");
        verify(userRepository, times(1)).findByUsername("user1");
    }
}
