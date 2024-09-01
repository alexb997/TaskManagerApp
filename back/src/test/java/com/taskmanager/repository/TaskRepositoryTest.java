//package com.taskmanager.repository;
//
//import com.taskmanager.model.Task;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@ActiveProfiles("test")
//class TaskRepositoryTest {
//
//    @Autowired
//    private TaskRepository taskRepository;
//
//    @Test
//    void testSaveTask() {
//        LocalDateTime now = LocalDateTime.now();
//        Task task = new Task(null, "Task 1", "Description 1", "PENDING", now, now.plusDays(1));
//        Task savedTask = taskRepository.save(task);
//
//        assertNotNull(savedTask.getId(), "Task ID should not be null after saving");
//        assertEquals("Task 1", savedTask.getName(), "Task name should match");
//        assertEquals("Description 1", savedTask.getDescription(), "Task description should match");
//        assertEquals("PENDING", savedTask.getStatus(), "Task status should match");
//        assertEquals(now, savedTask.getCreatedDate(), "Task createdDate should match");
//        assertEquals(now.plusDays(1), savedTask.getDueDate(), "Task dueDate should match");
//    }
//
//    @Test
//    void testFindById() {
//        LocalDateTime now = LocalDateTime.now();
//        Task task = new Task(null, "Task 2", "Description 2", "PENDING", now, now.plusDays(1));
//        Task savedTask = taskRepository.save(task);
//
//        Optional<Task> retrievedTask = taskRepository.findById(savedTask.getId());
//        assertTrue(retrievedTask.isPresent(), "Task should be present");
//        assertEquals("Task 2", retrievedTask.get().getName(), "Task name should match");
//        assertEquals("Description 2", retrievedTask.get().getDescription(), "Task description should match");
//        assertEquals("PENDING", retrievedTask.get().getStatus(), "Task status should match");
//        assertEquals(now, retrievedTask.get().getCreatedDate(), "Task createdDate should match");
//        assertEquals(now.plusDays(1), retrievedTask.get().getDueDate(), "Task dueDate should match");
//    }
//
//    @Test
//    void testFindAll() {
//        LocalDateTime now = LocalDateTime.now();
//        taskRepository.save(new Task(null, "Task 1", "Description 1", "PENDING", now, now.plusDays(1)));
//        taskRepository.save(new Task(null, "Task 2", "Description 2", "COMPLETED", now, now.plusDays(2)));
//
//        List<Task> tasks = taskRepository.findAll();
//        assertEquals(2, tasks.size(), "There should be two tasks in the repository");
//    }
//
//    @Test
//    void testDeleteById() {
//        LocalDateTime now = LocalDateTime.now();
//        Task task = new Task(null, "Task 3", "Description 3", "PENDING", now, now.plusDays(1));
//        Task savedTask = taskRepository.save(task);
//
//        taskRepository.deleteById(savedTask.getId());
//
//        Optional<Task> deletedTask = taskRepository.findById(savedTask.getId());
//        assertFalse(deletedTask.isPresent(), "Task should be deleted");
//    }
//
//    @Test
//    void testFindAllEmpty() {
//        List<Task> tasks = taskRepository.findAll();
//        assertTrue(tasks.isEmpty(), "Task repository should be empty");
//    }
//}
