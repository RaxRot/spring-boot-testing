package com.raxrot.springboot.repository;

import com.raxrot.springboot.entity.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Employee Repository Tests")
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @DisplayName("Junit test for save employee")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        Employee employee = Employee.builder()
                .firstName("Vlad")
                .lastName("Bulahov")
                .email("vladbulahov@gmail.com")
                .build();

        Employee savedEmployee = employeeRepository.save(employee);

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
        assertThat(savedEmployee.getFirstName()).isEqualTo(employee.getFirstName());

        Optional<Employee> found = employeeRepository.findById(savedEmployee.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo(employee.getEmail());
    }

    @DisplayName("Junit test for get all employees")
    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeesList() {
        Employee employee1 = Employee.builder()
                .firstName("Vlad")
                .lastName("Bulahov")
                .email("vladbulahov@gmail.com")
                .build();
        Employee employee2 = Employee.builder()
                .firstName("Dadari")
                .lastName("Hukallo")
                .email("daria@gmail.com")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        List<Employee> employees = employeeRepository.findAll();

        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(2);
    }

    @DisplayName("Junit test for get employee by id")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
        Employee employee = Employee.builder()
                .firstName("Vlad")
                .lastName("Bulahov")
                .email("vladbulahov@gmail.com")
                .build();
        employeeRepository.save(employee);

        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.getFirstName()).isEqualTo(employee.getFirstName());
        assertThat(employeeDB.getId()).isGreaterThan(0);
    }

    @DisplayName("Junit test for find employee by email")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
        Employee employee = Employee.builder()
                .firstName("Vlad")
                .lastName("Bulahov")
                .email("vladbulahov@gmail.com")
                .build();
        employeeRepository.save(employee);
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();
        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.getId()).isGreaterThan(0);
    }

    @DisplayName("Junit test for update employee")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        Employee employee = Employee.builder()
                .firstName("Vlados")
                .lastName("Bulahovos")
                .email("vladbulahovos@gmail.com")
                .build();
        employeeRepository.save(employee);
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setFirstName("Vlad");
        savedEmployee.setLastName("Bulahov");
        savedEmployee.setEmail("vladbulahov@gmail.com");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getId()).isGreaterThan(0);
        assertThat(updatedEmployee.getLastName()).isEqualTo("Bulahov");
        assertThat(updatedEmployee.getFirstName()).isEqualTo(savedEmployee.getFirstName());
    }

    @DisplayName("Junit test for delete employee")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemovedEmployee() {
        Employee employee = Employee.builder()
                .firstName("Vlad")
                .lastName("Bulahov")
                .email("vladbulahov@gmail.com")
                .build();
        employeeRepository.save(employee);
        long countBeforeDelete = employeeRepository.count();
        employeeRepository.deleteById(employee.getId());
        long countAfterDelete = employeeRepository.count();
        Optional<Employee> found = employeeRepository.findById(employee.getId());
        assertThat(found).isEmpty();
        assertThat(countBeforeDelete).isGreaterThan(countAfterDelete);
    }

}