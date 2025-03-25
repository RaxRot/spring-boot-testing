package com.raxrot.springboot.service;

import com.raxrot.springboot.entity.Employee;
import com.raxrot.springboot.repository.EmployeeRepository;
import com.raxrot.springboot.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
      employee = Employee.builder()
                .id(1L)
                .firstName("Vlad")
                .lastName("Bulahov")
                .email("vlad@gmail.com")
                .build();
    }

    @DisplayName("Test save service")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {

        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);
        Employee savedEmployee = employeeService.saveEmployee(employee);
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isEqualTo(employee.getId());
    }

    @DisplayName("Test give all service")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeeList() {
        Employee employee2 = Employee.builder()
                .id(2L)
                .firstName("Vlad2")
                .lastName("Bulahov2")
                .email("vlad2@gmail.com")
                .build();
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee2));
        List<Employee> allEmployees = employeeService.getAllEmployees();

        assertThat(allEmployees).isNotNull();
        assertThat(allEmployees.size()).isEqualTo(2);
        assertThat(allEmployees.contains(employee)).isTrue();
    }

    @DisplayName("Test getById service")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        Employee savedEmployee=employeeService.getEmployeeById(1L).get();

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isEqualTo(1L);
    }

    @DisplayName("Test update employee")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("vladupd@gmail.com");

        Employee updatedEmployee = employeeService.updateEmployee(employee);
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getEmail()).isEqualTo(employee.getEmail());
    }

    @DisplayName("Test delete method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        willDoNothing().given(employeeRepository).deleteById(1L);
        employeeService.deleteEmployee(1L);
        verify(employeeRepository,times(1)).deleteById(1L);
    }
}
