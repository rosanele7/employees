package com.employees.controller;

import com.employees.model.Employee;
import com.employees.request.ShortEmployee;
import com.employees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1_0/employees")
public class EmployeeController {
    private final static String DEFAULT_SORT_BY = "lastName";
    private final static int DEFAULT_PAGE_NUMBER = 0;
    private final static int DEFAULT_PAGE_SIZE = 10;
    private final EmployeeService service;

    @Autowired
    EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Employee> getEmployees(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                       @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                       @RequestParam(value = "sortBy", required = false) String sortBy) {
        if (pageNumber == null) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        if (sortBy == null) {
            sortBy = DEFAULT_SORT_BY;
        }

        Page<Employee> page = service.getEmployees(pageNumber, pageSize, sortBy);
        return page;
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        Employee employee = service.getEmployeeById(id);
        return employee;
    }

    @PostMapping
    public Employee setEmployee(@RequestBody Employee newEmployee) {
        Employee employee = service.setEmployee(newEmployee);
        return employee;
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody ShortEmployee shortEmployee) {
        Employee employee = service.updateEmployee(id, shortEmployee);
        return employee;
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
    }
}
