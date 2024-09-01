package com.taskmanager.model;

import org.junit.jupiter.api.Test;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    void testUserConstructorAndGetters() {
        User user = new User(1L, "testuser", "password123");

        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testUserSetters() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");

        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testUsernameValidation() {
        User user = new User();
        user.setUsername(""); // Invalid username
        user.setPassword("password123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Username is mandatory", violations.iterator().next().getMessage());
    }

    @Test
    void testPasswordValidation() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword(""); // Invalid password

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Password is mandatory", violations.iterator().next().getMessage());
    }

    @Test
    void testValidUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }
}
