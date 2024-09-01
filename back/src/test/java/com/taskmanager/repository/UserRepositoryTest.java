//package com.taskmanager.repository;
//
//import com.taskmanager.model.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@ActiveProfiles("test")
//class UserRepositoryTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    void testSaveUser() {
//        User user = new User(null, "testuser", "password123");
//        User savedUser = userRepository.save(user);
//
//        assertNotNull(savedUser.getId(), "User ID should not be null after saving");
//        assertEquals("testuser", savedUser.getUsername(), "Username should match");
//        assertEquals("password123", savedUser.getPassword(), "Password should match");
//    }
//
//    @Test
//    void testFindById() {
//        User user = new User(null, "user1", "password1");
//        User savedUser = userRepository.save(user);
//
//        Optional<User> retrievedUser = userRepository.findById(savedUser.getId());
//        assertTrue(retrievedUser.isPresent(), "User should be present");
//        assertEquals("user1", retrievedUser.get().getUsername(), "Username should match");
//        assertEquals("password1", retrievedUser.get().getPassword(), "Password should match");
//    }
//
//    @Test
//    void testFindByUsername() {
//        User user = new User(null, "uniqueuser", "password123");
//        userRepository.save(user);
//
//        Optional<User> retrievedUser = userRepository.findByUsername("uniqueuser");
//        assertTrue(retrievedUser.isPresent(), "User should be found by username");
//        assertEquals("uniqueuser", retrievedUser.get().getUsername(), "Username should match");
//        assertEquals("password123", retrievedUser.get().getPassword(), "Password should match");
//    }
//
//    @Test
//    void testFindByUsernameNotFound() {
//        Optional<User> retrievedUser = userRepository.findByUsername("nonexistentuser");
//        assertFalse(retrievedUser.isPresent(), "User should not be found by username");
//    }
//
//    @Test
//    void testDeleteById() {
//        User user = new User(null, "userToDelete", "password");
//        User savedUser = userRepository.save(user);
//
//        userRepository.deleteById(savedUser.getId());
//
//        Optional<User> deletedUser = userRepository.findById(savedUser.getId());
//        assertFalse(deletedUser.isPresent(), "User should be deleted");
//    }
//}
