package com.personal.taskmanager.controllers;

import com.personal.taskmanager.model.Task;
import com.personal.taskmanager.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostMapping
    public Optional<Object> createTask(@RequestBody Task task) {
        return Optional.empty();
    }

    @GetMapping
    public Optional<Object> getAllTasks() {
        return Optional.empty();
    }

    @GetMapping("/{id}")
    public Optional<Object> getTaskById(@PathVariable Long id) {
        return Optional.empty();
    }

    @PutMapping("/{id}")
    public Optional<Object> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {

        return Optional.empty();
    }

    @DeleteMapping("/{id}")
    public Optional<Object> deleteTask(@PathVariable Long id) {

        return Optional.empty();
    }
}
