package com.raxrot.sbtesting.service;

import com.raxrot.sbtesting.entity.Employee;
import com.raxrot.sbtesting.repository.EmployeeRepository;

import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> employeeOptional=employeeRepository.findByEmail(employee.getEmail());
        if(employeeOptional.isPresent()){
            throw new RuntimeException("Employee already exists");
        }
        return employeeRepository.save(employee);
    }
}
