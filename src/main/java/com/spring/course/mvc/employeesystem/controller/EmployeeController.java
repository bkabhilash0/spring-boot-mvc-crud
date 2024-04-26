package com.spring.course.mvc.employeesystem.controller;

import com.spring.course.mvc.employeesystem.model.Employee;
import com.spring.course.mvc.employeesystem.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("")
    public String employees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees/list-employees";
    }

    @GetMapping("/add")
    public String addEmployee(Model model) {
//        model.addAttribute("update", false);
        model.addAttribute("employee", new Employee());
        return "employees/add-employee";
    }

    @PostMapping("/add")
    public String processEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "employees/add-employee";
        }
        this.employeeService.addEmployee(employee);
        redirectAttributes.addFlashAttribute("flashMessage", "Employee added successfully");
        return "redirect:/employees";
    }

    @GetMapping("/update")
    public String updateEmployee(@RequestParam("employeeId") int id, Model model) {
        Employee employee = this.employeeService.getEmployeeById(id);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with id " + id + " not found");
        }
        model.addAttribute("update", true);
        model.addAttribute("employee", employee);
        return "employees/add-employee";
    }

    @PostMapping("/update")
    public String processUpdateEmp(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "employees/add-employee";
        }
        this.employeeService.updateEmployee(employee);
        redirectAttributes.addFlashAttribute("flashMessage", "Employee Updated successfully");
        return "redirect:/employees";
    }

    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam("employeeId") Integer id, RedirectAttributes redirectAttributes) {
        this.employeeService.deleteEmployeeById(id);
        redirectAttributes.addFlashAttribute("flashMessage", "Employee deleted successfully");
        return "redirect:/employees";
    }
}
