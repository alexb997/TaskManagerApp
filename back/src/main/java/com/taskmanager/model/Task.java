package com.taskmanager.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime dueDate;

    private String creator;
    private String assignedUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(status, task.status) &&
                Objects.equals(createdDate, task.createdDate) &&
                Objects.equals(dueDate, task.dueDate) &&
                Objects.equals(creator, task.creator) &&
                Objects.equals(assignedUser, task.assignedUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status, createdDate, dueDate, creator, assignedUser);
    }
}
