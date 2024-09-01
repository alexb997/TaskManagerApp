package com.taskmanager.service;

import com.taskmanager.model.Task;
import com.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        task1 = new Task(1L, "Task 1", "Description 1", "PENDING", LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        task2 = new Task(2L, "Task 2", "Description 2", "COMPLETED", LocalDateTime.now(), LocalDateTime.now().plusDays(2));
    }

    @Test
    void testCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task1);

        Task createdTask = taskService.createTask(task1);

        assertNotNull(createdTask, "Created task should not be null");
        assertEquals("Task 1", createdTask.getName(), "Task name should match");
        verify(taskRepository, times(1)).save(task1);
    }

    @Test
    void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> tasks = taskService.getAllTasks();

        assertNotNull(tasks, "Task list should not be null");
        assertEquals(2, tasks.size(), "Task list size should match");
        assertTrue(tasks.contains(task1), "Task list should contain task1");
        assertTrue(tasks.contains(task2), "Task list should contain task2");
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));

        Optional<Task> task = taskService.getTaskById(1L);

        assertTrue(task.isPresent(), "Task should be present");
        assertEquals("Task 1", task.get().getName(), "Task name should match");
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateTask() {
        Task updatedTaskDetails = new Task(1L, "Updated Task", "Updated Description", "IN_PROGRESS", LocalDateTime.now(), LocalDateTime.now().plusDays(3));

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTaskDetails);

        Task updatedTask = taskService.updateTask(1L, updatedTaskDetails);

        assertNotNull(updatedTask, "Updated task should not be null");
        assertEquals("Updated Task", updatedTask.getName(), "Updated task name should match");
        assertEquals("Updated Description", updatedTask.getDescription(), "Updated task description should match");
        assertEquals("IN_PROGRESS", updatedTask.getStatus(), "Updated task status should match");
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(updatedTaskDetails);
    }

    @Test
    void testUpdateTask_NotFound() {
        Task updatedTaskDetails = new Task(1L, "Updated Task", "Updated Description", "IN_PROGRESS", LocalDateTime.now(), LocalDateTime.now().plusDays(3));

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            taskService.updateTask(1L, updatedTaskDetails);
        });

        assertEquals("Task not found with id: 1", thrownException.getMessage(), "Exception message should match");
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void testDeleteTask() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        boolean isDeleted = taskService.deleteTask(1L);

        assertTrue(isDeleted, "Task should be deleted");
        verify(taskRepository, times(1)).existsById(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTask_NotFound() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        boolean isDeleted = taskService.deleteTask(1L);

        assertFalse(isDeleted, "Task should not be deleted if not found");
        verify(taskRepository, times(1)).existsById(1L);
        verify(taskRepository, never()).deleteById(1L);
    }
}
