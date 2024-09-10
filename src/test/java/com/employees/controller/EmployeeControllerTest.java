package com.employees.controller;

import com.employees.exceptions.NoSuchSupplierExistException;
import com.employees.exceptions.SupplierAlreadyExistsException;
import com.employees.model.Employee;
import com.employees.repository.EmployeeRepository;
import com.employees.request.ShortEmployee;
import com.employees.service.EmployeeService;
import com.employees.service.EmployeeServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.aspectj.apache.bcel.classfile.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmployeeControllerTest {

    @Mock
    private EmployeeRepository repository;

    private EmployeeController controller;
    private EmployeeService employeeService;

    @BeforeEach
    public void beforeAll() {
        MockitoAnnotations.openMocks(this);

        employeeService = new EmployeeServiceImpl(repository);
        controller = new EmployeeController(employeeService);
    }

    @Test
    void getAllEmployees_Basic() {
        List<Employee> employees = Arrays.asList(
            new Employee("OneFirstName", "OneLastName", "one@email.com"),
            new Employee("SecondFirstName", "SecondLastName", "second@email.com"),
            new Employee("ThirdFirstName", "ThirdLastName", "third@email.com"),
            new Employee("FourthFirstName", "FourthLastName", "fourth@email.com"),
            new Employee("FifthFirstName", "FifthLastName", "fifth@email.com"),
            new Employee("SixthFirstName", "SixthLastName", "sixth@email.com"),
            new Employee("SeventhFirstName", "SeventhLastName", "seventh@email.com"),
            new Employee("EighthFirstName", "EighthLastName", "eighth@email.com"),
            new Employee("NinthFirstName", "NinthLastName", "ninth@email.com"),
            new Employee("TenthFirstName", "TenthLastName", "tenth@email.com"));
        Pageable pageable = PageRequest.of(0, 5, Sort.by("lastName"));
        when(repository.findAll(any(Pageable.class)))
            .thenReturn(new PageImpl<>(employees, pageable, 1));

        Page<Employee> employeePage = controller.getEmployees(0, 10, "lastName");
        assertEquals(2, employeePage.getTotalPages());
        verify(repository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void setEmployee_Basic() {
        when(repository.save(any(Employee.class)))
            .thenReturn(new Employee("EleventhFirstName", "EleventhLastName", "eleventh@email.com"));

        Employee employee = controller.setEmployee(
            new Employee("EleventhFirstName", "EleventhLastName", "eleventh@email.com"));

        assertEquals("EleventhFirstName", employee.getFirstName());
        verify(repository, times(1)).save(any(Employee.class));
    }

    @Test
    void setEmployee_AlreadyExistentEmployee() {
        when(repository.save(any(Employee.class))).thenThrow(new SupplierAlreadyExistsException());

        Exception exception = assertThrows(SupplierAlreadyExistsException.class, () -> {
           controller.setEmployee(
               new Employee("EleventhFirstName", "EleventhLastName", "eleventh@email.com"));
        });

        assertTrue(exception.getMessage().contains("eleventh@email.com"));
    }

    @Test
    void getEmployeeById_Basic() {
        when(repository.findById(anyLong()))
            .thenReturn(Optional.of(new Employee("TwelfthFirstName", "TwelfthLastName", "twelfth@email.com")));

        Employee employee = controller.getEmployeeById(12L);

        assertEquals("TwelfthFirstName", employee.getFirstName());
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void getEmployeeById_NonExistentEmployee() {
        when(repository.findById(anyLong()))
            .thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchSupplierExistException.class, () -> {
           controller.getEmployeeById(14L);
        });

        assertTrue(exception.getMessage().contains("14"));
    }

    @Test
    void updateEmployee_Basic() {
        Employee employee = new Employee(
            "ThirteenthFirstName",
            "ThirteenthLastName",
            "thirteenth@email.com");
        employee.setId(13L);
        when(repository.findById(anyLong())).thenReturn(Optional.of(employee));

        Employee updatedEmployee = new Employee(
            "ThirteenthFirstName_One",
            "ThirteenthLastName_One",
            "thirteenth@email.com");
        updatedEmployee.setId(13L);
        when(repository.save(any(Employee.class))).thenReturn(updatedEmployee);

        Employee updatedEmployeeResult = controller.updateEmployee(13L,
            new ShortEmployee("ThirteenthFirstName_One", "ThirteenthLastName_One"));

        assertEquals(13L, updatedEmployee.getId());
        assertEquals("ThirteenthFirstName_One", updatedEmployeeResult.getFirstName());
        verify(repository, times(1)).save(any(Employee.class));
    }

    @Test
    void updateEmployee_NonExistentEmployee() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchSupplierExistException.class, () -> {
            controller.updateEmployee(15L,
                new ShortEmployee("FifteenthFirstName_One", "FifteenthLastName_One"));
        });

        assertTrue(exception.getMessage().contains("15"));
    }

    @Test
    void deleteEmployee_Basic() {
        assertDoesNotThrow(() -> {
            controller.deleteEmployee(13L);
        });
    }
}