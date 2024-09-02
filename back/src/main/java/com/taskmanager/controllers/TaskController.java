package com.taskmanager.controllers;

import com.taskmanager.model.Task;
import com.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody Task task) {
        try {
            taskService.createTask(task);
            return new ResponseEntity<>("Task created successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        try {
            taskService.updateTask(id, taskDetails);
            return new ResponseEntity<>("Task updated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        boolean isDeleted = taskService.deleteTask(id);
        if (isDeleted) {
            return new ResponseEntity<>("Task deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Task>> getAllTasksByUsername(@PathVariable String username) {
        List<Task> tasks = taskService.getAllTasksByUsername(username);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/created-by/{username}")
    public ResponseEntity<List<Task>> getTasksCreatedByUser(@PathVariable String username) {
        List<Task> tasks = taskService.getTasksCreatedByUser(username);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/assigned-to/{username}")
    public ResponseEntity<List<Task>> getTasksAssignedToUser(@PathVariable String username) {
        List<Task> tasks = taskService.getTasksAssignedToUser(username);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
}
