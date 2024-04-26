package com.spring.course.mvc.employeesystem.repository;

import com.spring.course.mvc.employeesystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
}
