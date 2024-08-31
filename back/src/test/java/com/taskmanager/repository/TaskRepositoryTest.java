package com.taskmanager.repository;

import com.taskmanager.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void testSaveTask() {
        Task task = new Task(null, "Task 1", "Description 1", "PENDING");
        Task savedTask = taskRepository.save(task);

        assertNotNull(savedTask.getId());
        assertEquals("Task 1", savedTask.getName());
    }

    @Test
    void testFindById() {
        Task task = new Task(null, "Task 2", "Description 2", "PENDING");
        Task savedTask = taskRepository.save(task);

        Optional<Task> retrievedTask = taskRepository.findById(savedTask.getId());
        assertTrue(retrievedTask.isPresent());
        assertEquals("Task 2", retrievedTask.get().getName());
    }

    @Test
    void testFindAll() {
        taskRepository.save(new Task(null, "Task 1", "Description 1", "PENDING"));
        taskRepository.save(new Task(null, "Task 2", "Description 2", "COMPLETED"));

        List<Task> tasks = taskRepository.findAll();
        assertEquals(2, tasks.size());
    }

    @Test
    void testDeleteById() {
        Task task = new Task(null, "Task 3", "Description 3", "PENDING");
        Task savedTask = taskRepository.save(task);

        taskRepository.deleteById(savedTask.getId());

        Optional<Task> deletedTask = taskRepository.findById(savedTask.getId());
        assertFalse(deletedTask.isPresent());
    }
}
