package com.taskmanager.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    @Test
    void testTaskConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        Task task = new Task(1L, "Test", "Description", "PENDING", now, now.plusDays(1));

        assertEquals(1L, task.getId());
        assertEquals("Test", task.getName());
        assertEquals("Description", task.getDescription());
        assertEquals("PENDING", task.getStatus());
        assertEquals(now, task.getCreatedDate());
        assertEquals(now.plusDays(1), task.getDueDate());
    }

    @Test
    void testTaskSetters() {
        Task task = new Task();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime future = now.plusDays(1);

        task.setId(1L);
        task.setName("Test");
        task.setDescription("Description");
        task.setStatus("COMPLETED");
        task.setCreatedDate(now);
        task.setDueDate(future);

        assertEquals(1L, task.getId());
        assertEquals("Test", task.getName());
        assertEquals("Description", task.getDescription());
        assertEquals("COMPLETED", task.getStatus());
        assertEquals(now, task.getCreatedDate());
        assertEquals(future, task.getDueDate());
    }
}
