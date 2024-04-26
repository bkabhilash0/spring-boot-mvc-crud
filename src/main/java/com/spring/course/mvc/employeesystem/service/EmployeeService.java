package com.spring.course.mvc.employeesystem.service;

import com.spring.course.mvc.employeesystem.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Employee getEmployeeById(int id);

    void addEmployee(Employee employee);

    void updateEmployee(Employee employee);

    void deleteEmployeeById(int id);
}
