// package com.taskmanager.service;

// import com.taskmanager.model.Task;
// import com.taskmanager.repository.TaskRepository;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import java.util.Arrays;
// import java.util.List;
// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// class TaskServiceTest {

//     @Mock
//     private TaskRepository taskRepository;

//     @InjectMocks
//     private TaskService taskService;

//     private Task task1;
//     private Task task2;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//         task1 = new Task(1L, "Task 1", "Description 1", "PENDING");
//         task2 = new Task(2L, "Task 2", "Description 2", "COMPLETED");
//     }

//     @Test
//     void testCreateTask() {
//         when(taskRepository.save(any(Task.class))).thenReturn(task1);

//         Task createdTask = taskService.createTask(task1);

//         assertNotNull(createdTask);
//         assertEquals("Task 1", createdTask.getName());
//         verify(taskRepository, times(1)).save(task1);
//     }

//     @Test
//     void testGetAllTasks() {
//         when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

//         List<Task> tasks = taskService.getAllTasks();

//         assertNotNull(tasks);
//         assertEquals(2, tasks.size());
//         verify(taskRepository, times(1)).findAll();
//     }

//     @Test
//     void testGetTaskById() {
//         when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));

//         Optional<Task> task = taskService.getTaskById(1L);

//         assertTrue(task.isPresent());
//         assertEquals("Task 1", task.get().getName());
//         verify(taskRepository, times(1)).findById(1L);
//     }

//     @Test
//     void testUpdateTask() {
//         when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
//         when(taskRepository.save(any(Task.class))).thenReturn(task1);

//         Task updatedTask = taskService.updateTask(1L, task1);

//         assertNotNull(updatedTask);
//         assertEquals("Task 1", updatedTask.getName());
//         verify(taskRepository, times(1)).findById(1L);
//         verify(taskRepository, times(1)).save(task1);
//     }

//     @Test
//     void testDeleteTask() {
//         when(taskRepository.existsById(1L)).thenReturn(true);

//         boolean isDeleted = taskService.deleteTask(1L);

//         assertTrue(isDeleted);
//         verify(taskRepository, times(1)).existsById(1L);
//         verify(taskRepository, times(1)).deleteById(1L);
//     }

//     @Test
//     void testDeleteTask_NotFound() {
//         when(taskRepository.existsById(1L)).thenReturn(false);

//         boolean isDeleted = taskService.deleteTask(1L);

//         assertFalse(isDeleted);
//         verify(taskRepository, times(1)).existsById(1L);
//         verify(taskRepository, never()).deleteById(1L);
//     }
// }
