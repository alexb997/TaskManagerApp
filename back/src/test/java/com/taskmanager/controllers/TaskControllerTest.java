package com.taskmanager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanager.model.Task;
import com.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateTask() throws Exception {
        Task task = new Task(null, "Task 1", "Description 1", "PENDING", LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        when(taskService.createTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Task created successfully"));
    }

    @Test
    void testGetAllTasks() throws Exception {
        Task task1 = new Task(1L, "Task 1", "Description 1", "PENDING", LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        Task task2 = new Task(2L, "Task 2", "Description 2", "COMPLETED", LocalDateTime.now(), LocalDateTime.now().plusDays(2));

        when(taskService.getAllTasks()).thenReturn(Arrays.asList(task1, task2));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Task 1")))
                .andExpect(jsonPath("$[1].name", is("Task 2")));
    }

    @Test
    void testGetTaskById() throws Exception {
        Task task = new Task(1L, "Task 1", "Description 1", "PENDING", LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        when(taskService.getTaskById(1L)).thenReturn(Optional.of(task));

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Task 1")))
                .andExpect(jsonPath("$.description", is("Description 1")))
                .andExpect(jsonPath("$.status", is("PENDING")));
    }

    @Test
    void testGetTaskById_NotFound() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateTask() throws Exception {
        Task task = new Task(1L, "Updated Task", "Updated Description", "COMPLETED", LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(task);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(content().string("Task updated successfully"));
    }

    @Test
    void testUpdateTask_NotFound() throws Exception {
        Task task = new Task(1L, "Updated Task", "Updated Description", "COMPLETED", LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        when(taskService.updateTask(eq(1L), any(Task.class)))
                .thenThrow(new RuntimeException("Task not found with id: 1"));

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task not found with id: 1"));
    }

    @Test
    void testDeleteTask() throws Exception {
        when(taskService.deleteTask(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Task deleted successfully"));
    }

    @Test
    void testDeleteTask_NotFound() throws Exception {
        when(taskService.deleteTask(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task not found"));
    }
}
