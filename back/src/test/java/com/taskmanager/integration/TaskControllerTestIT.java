package com.taskmanager.integration;

import com.taskmanager.model.Task;
import com.taskmanager.repository.TaskRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTestIT {

    @Autowired
    private TaskRepository taskRepository;

    private RestTemplate restTemplate;
    private String baseUrl;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
        baseUrl = "http://localhost:8080/api/tasks";
    }

    @Test
    public void testCreateTask() {
        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("Test Description");
        task.setStatus("Pending");
        task.setCreatedDate(LocalDateTime.now());
        task.setDueDate(LocalDateTime.now().plusDays(1));

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, task, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(taskRepository.findById(task.getId()));
    }

    @Test
    public void testGetAllTasks() {
        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl, List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetTaskById() {
        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("Test Description");
        task.setStatus("Pending");
        task.setCreatedDate(LocalDateTime.now());
        task.setDueDate(LocalDateTime.now().plusDays(1));
        task = taskRepository.save(task);

        ResponseEntity<Task> response = restTemplate.getForEntity(baseUrl + "/" + task.getId(), Task.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(task.getName(), response.getBody().getName());
    }

    @Test
    public void testDeleteTask() {
        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("Test Description");
        task.setStatus("Pending");
        task.setCreatedDate(LocalDateTime.now());
        task.setDueDate(LocalDateTime.now().plusDays(1));
        task = taskRepository.save(task);

        restTemplate.delete(baseUrl + "/" + task.getId());

        assertEquals(false, taskRepository.existsById(task.getId()));
    }
}
