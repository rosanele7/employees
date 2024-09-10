package com.employees.service;

import com.employees.model.Employee;
import com.employees.request.ShortEmployee;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    public Page<Employee> getEmployees(int pageNumber, int pageSize, String sortBy);

    public Employee setEmployee(Employee employee);

    public Employee getEmployeeById(Long id);

    public Employee updateEmployee(Long id, ShortEmployee employee);

    public void deleteEmployee(Long id);
}
