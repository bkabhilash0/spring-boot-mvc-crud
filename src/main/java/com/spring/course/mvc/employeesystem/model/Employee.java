package com.spring.course.mvc.employeesystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "First Name is Required")
    @Size(min = 5,message = "Must be Minimum of 5 Characters Long!")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Last Name is Required")
    @NotBlank(message = "Last Name is Required")
    private String lastName;

    @NotNull(message = "Email is Required")
    @NotBlank(message = "Email is Required")
    @Email(message = "Enter a Valid Email")
    @Column(name = "email")
    private String email;
}
