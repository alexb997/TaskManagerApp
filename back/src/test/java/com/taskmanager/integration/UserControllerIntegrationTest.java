package com.taskmanager.integration;

import com.taskmanager.model.User;
import com.taskmanager.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private RestTemplate restTemplate;
    private String baseUrl;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
        baseUrl = "http://localhost:8080/api/users";
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, user, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(userRepository.findByUsername("testuser"));
    }

    @Test
    public void testGetAllUsers() {
        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl, List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setUsername("testuser2");
        user.setPassword("password123");
        user = userRepository.save(user);

        ResponseEntity<User> response = restTemplate.getForEntity(baseUrl + "/" + user.getId(), User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getUsername(), response.getBody().getUsername());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setUsername("testuser3");
        user.setPassword("password123");
        user = userRepository.save(user);

        user.setPassword("newpassword123");
        restTemplate.put(baseUrl + "/" + user.getId(), user);

        User updatedUser = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(updatedUser);
        assertEquals("newpassword123", updatedUser.getPassword());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setUsername("testuser4");
        user.setPassword("password123");
        user = userRepository.save(user);

        restTemplate.delete(baseUrl + "/" + user.getId());

        assertFalse(userRepository.existsById(user.getId()));
    }

    @Test
    public void testLoginUserSuccess() {
        User user = new User();
        user.setUsername("testuser5");
        user.setPassword("password123");
        user = userRepository.save(user);

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/login", user, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User logged in successfully", response.getBody());
    }

    @Test
    public void testLoginUserFailure() {
        User user = new User();
        user.setUsername("invaliduser");
        user.setPassword("wrongpassword");

        try {
            restTemplate.postForEntity(baseUrl + "/login", user, String.class);
            fail("Expected HttpClientErrorException");
        } catch (HttpClientErrorException ex) {
            assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
        }
    }
}
