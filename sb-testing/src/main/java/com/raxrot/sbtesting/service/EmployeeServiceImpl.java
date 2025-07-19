package com.raxrot.sbtesting.service;

import com.raxrot.sbtesting.dto.EmployeeDTO;
import com.raxrot.sbtesting.entity.Employee;
import com.raxrot.sbtesting.exception.ApiException;
import com.raxrot.sbtesting.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employee) {
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(employee.getEmail());

        if (employeeOptional.isPresent()) {
            throw new ApiException("Employee already exists with email: " + employee.getEmail());
        }

        Employee employeeEntity = modelMapper.map(employee, Employee.class);
        Employee savedEmployee = employeeRepository.save(employeeEntity);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee foundEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ApiException("Employee not found with id: " + id));
        return modelMapper.map(foundEmployee, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ApiException("Employee not found with id: " + id));

        existing.setFirstName(employeeDTO.getFirstName());
        existing.setLastName(employeeDTO.getLastName());
        existing.setEmail(employeeDTO.getEmail());

        Employee updated = employeeRepository.save(existing);
        return modelMapper.map(updated, EmployeeDTO.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ApiException("Employee not found with id: " + id));

        employeeRepository.delete(existing);
    }
}
