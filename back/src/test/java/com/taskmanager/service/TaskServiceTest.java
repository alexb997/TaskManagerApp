package com.taskmanager.service;

import com.taskmanager.model.Task;
import com.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        task1 = new Task(1L, "Task 1", "Description 1", "PENDING", LocalDateTime.now(), LocalDateTime.now());
        task2 = new Task(2L, "Task 2", "Description 2", "COMPLETED", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    public void testCreateTask() {
        Task task = new Task(null, "Task 1", "Description 1", "PENDING", null, null);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task);

        assertNotNull(createdTask);
        assertEquals("Task 1", createdTask.getName());
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
        LocalDateTime fixedTimestamp = LocalDateTime.of(2024, 9, 1, 15, 37, 50, 455058500);

        Task updatedTaskDetails = new Task(1L, "Updated Task", "Updated Description", "IN_PROGRESS", fixedTimestamp, fixedTimestamp.plusDays(3));

        Task existingTask = new Task(1L, "Existing Task", "Existing Description", "PENDING", fixedTimestamp, fixedTimestamp.plusDays(3));

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task updatedTask = taskService.updateTask(1L, updatedTaskDetails);

        assertNotNull(updatedTask, "Updated task should not be null");
        assertEquals("Updated Task", updatedTask.getName(), "Updated task name should match");
        assertEquals("Updated Description", updatedTask.getDescription(), "Updated task description should match");
        assertEquals("IN_PROGRESS", updatedTask.getStatus(), "Updated task status should match");
        assertEquals(fixedTimestamp, updatedTask.getCreatedDate(), "Created date should match the fixed timestamp");
        assertEquals(fixedTimestamp.plusDays(3), updatedTask.getDueDate(), "Due date should match the fixed timestamp plus 3 days");

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(updatedTaskDetails);
    }

    @Test
    void testUpdateTask_NotFound() {
        Task updatedTaskDetails = new Task(1L, "Updated Task", "Updated Description", "IN_PROGRESS", LocalDateTime.now(), LocalDateTime.now());

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            taskService.updateTask(1L, updatedTaskDetails);
        });

        assertEquals("Task not found with id: 1", thrownException.getMessage(), "Exception message should match");
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    public void testDeleteTask() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        boolean result = taskService.deleteTask(1L);

        assertTrue(result);
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
