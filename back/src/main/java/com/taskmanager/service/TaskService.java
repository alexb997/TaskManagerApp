package com.taskmanager.service;

import com.taskmanager.model.Task;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(Task task) {
        if (task.getAssignedUser() != null && !userRepository.existsByUsername(task.getAssignedUser())) {
            throw new IllegalArgumentException("Assigned username doesn't exist!");
        }
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task updateTask(Long id, Task taskDetails) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setName(taskDetails.getName());
            task.setDescription(taskDetails.getDescription());
            task.setStatus(taskDetails.getStatus());
            task.setDueDate(taskDetails.getDueDate());
            task.setCreator(taskDetails.getCreator());
            task.setAssignedUser(taskDetails.getAssignedUser());
            return taskRepository.save(task);
        } else {
            throw new RuntimeException("Task not found with id: " + id);
        }
    }

    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<Task> getAllTasksByUsername(String username) {
        return taskRepository.findAll()
                .stream()
                .filter(task -> username.equals(task.getCreator()) || username.equals(task.getAssignedUser()))
                .distinct() // Ensures the list contains unique tasks
                .collect(Collectors.toList());
    }

    // New method to get all tasks created by a specific user
    public List<Task> getTasksCreatedByUser(String username) {
        return taskRepository.findAll()
                .stream()
                .filter(task -> username.equals(task.getCreator()))
                .collect(Collectors.toList());
    }

    public List<Task> getTasksAssignedToUser(String username) {
        return taskRepository.findAll()
                .stream()
                .filter(task -> username.equals(task.getAssignedUser()))
                .collect(Collectors.toList());
    }
}
