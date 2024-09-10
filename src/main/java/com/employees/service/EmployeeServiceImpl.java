package com.employees.service;

import com.employees.exceptions.NoSuchSupplierExistException;
import com.employees.exceptions.SupplierAlreadyExistsException;
import com.employees.model.Employee;
import com.employees.repository.EmployeeRepository;
import com.employees.request.ShortEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Employee> getEmployees(int pageNumber, int pageSize, String sortBy) {
        Page<Employee> page = repository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)));
        return page;
    }

    @Override
    public Employee setEmployee(Employee employee) {
        try {
            return repository.save(employee);
        } catch (Exception ex) {
            throw new SupplierAlreadyExistsException("This employee[" + employee.getEmail() + "] already exist!");
        }
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = repository.findById(id);

        if (!optionalEmployee.isPresent()) {
            throw new NoSuchSupplierExistException("There isn't the employee with id: " + id);
        }

        return optionalEmployee.get();
    }

    @Override
    public Employee updateEmployee(Long id, ShortEmployee shortEmployee) {
        Optional<Employee> employee = repository.findById(id);

        if (employee.isPresent()) {
            Employee employeeToUpdate = employee.get();

            if (shortEmployee.getFirstName() != null
                && !employeeToUpdate.getFirstName().equals(shortEmployee.getFirstName())) {
                employeeToUpdate.setFirstName(shortEmployee.getFirstName());
            }

            if (shortEmployee.getLastName() != null
                && !employeeToUpdate.getLastName().equals(shortEmployee.getLastName())) {
                employeeToUpdate.setLastName(shortEmployee.getLastName());
            }

            return repository.save(employeeToUpdate);
        } else {
            throw new NoSuchSupplierExistException("There isn't the employee with id: " + id);
        }
    }

    @Override
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }

}
