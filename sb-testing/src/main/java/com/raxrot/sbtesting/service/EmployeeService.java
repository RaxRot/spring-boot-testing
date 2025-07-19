package com.raxrot.sbtesting.service;

import com.raxrot.sbtesting.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO saveEmployee(EmployeeDTO employee);
    List<EmployeeDTO>getAllEmployees();
    EmployeeDTO getEmployeeById(Long id);
    EmployeeDTO updateEmployee(Long id,EmployeeDTO employee);
    void deleteEmployee(Long id);
}
