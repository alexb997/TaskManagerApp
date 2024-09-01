package com.taskmanager.model;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "app_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is mandatory")
    @Column(nullable = false, unique = true)
    private String username;


    @NotBlank(message = "Password is mandatory")
    @Column(nullable = false)
    private String password;

}
