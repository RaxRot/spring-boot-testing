package com.raxrot.sbtesting.repository;

import com.raxrot.sbtesting.entity.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Save employee")
    void givenEmployee_whenSave_thenReturnEmployee() {
        //given
        Employee employee =  Employee.builder()
                .firstName("Vlad")
                .lastName("Bulahov")
                .email("vlad@gmail.com")
                .build();
        //when
        Employee savedEmployee = employeeRepository.save(employee);

        //then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Get all employees")
    void givenEmployees_whenGetAll_thenReturnEmployees() {
        //given
        Employee employee1 =  Employee.builder()
                .firstName("Vlad")
                .lastName("Bulahov")
                .email("vlad@gmail.com")
                .build();
        Employee employee2 =  Employee.builder()
                .firstName("Nadiia")
                .lastName("Makarova")
                .email("nadiia@gmail.com")
                .build();
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        //when
        List<Employee> employees = employeeRepository.findAll();

        //then
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Get employee by id")
     void givenEmployee_whenGetEmployeeById_thenReturnEmployee() {
        //given
        Employee employee =  Employee.builder()
                .firstName("Vlad")
                .lastName("Bulahov")
                .email("vlad@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when
        Employee employeeById = employeeRepository.findById(employee.getId()).get();

        //then
        assertThat(employeeById).isNotNull();
    }

    @Test
    @DisplayName("Get employee by email")
    void givenEmployee_whenGetEmployeeByEmail_thenReturnEmployee() {
        //given
        Employee employee =  Employee.builder()
                .firstName("Vlad")
                .lastName("Bulahov")
                .email("vlad@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when
        Employee employeeByEmail=employeeRepository.findByEmail(employee.getEmail()).get();

        //then
        assertThat(employeeByEmail).isNotNull();
        assertThat(employeeByEmail.getEmail()).isEqualTo(employee.getEmail());
    }

    @Test
    @DisplayName("Update employee")
    void givenEmployee_whenUpdateEmployee_thenReturnEmployee() {
        //given
        Employee employee =  Employee.builder()
                .firstName("Vlad")
                .lastName("Bulahov")
                .email("vlad@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when
        Employee employeeInDb = employeeRepository.findById(employee.getId()).get();
        employeeInDb.setFirstName("Nadiia");
        employeeInDb.setLastName("Makarova");
        Employee updatedEmployee = employeeRepository.save(employeeInDb);

        //then
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getId()).isEqualTo(employee.getId());
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Nadiia");
        assertThat(updatedEmployee.getLastName()).isEqualTo("Makarova");
        assertThat(updatedEmployee.getEmail()).isEqualTo("vlad@gmail.com");
    }

    @Test
    @DisplayName("Delete employee")
    void givenEmployee_whenDeleteEmployee_thenReturnEmployee() {
        //given
        Employee employee =  Employee.builder()
                .firstName("Vlad")
                .lastName("Bulahov")
                .email("vlad@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when
        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        //then
        assertThat(employeeOptional.isPresent()).isFalse();
    }

    @Test
    @DisplayName("JPQL")
    void givenEmployee_whenJPQL_thenReturnEmployee() {
        //given
        Employee employee =  Employee.builder()
                .firstName("Vlad")
                .lastName("Bulahov")
                .email("vlad@gmail.com")
                .build();
       Employee savedEmployee = employeeRepository.save(employee);

        //when
        Employee employeeInDb=employeeRepository.findByJPQL(savedEmployee.getFirstName(), savedEmployee.getLastName());

        //then
        assertThat(employeeInDb).isNotNull();
        assertThat(employeeInDb.getId()).isGreaterThan(0);
    }
    @Test
    @DisplayName("Not found by id")
    void whenGetByWrongId_thenReturnEmpty() {
        //when
        Optional<Employee> employeeOptional = employeeRepository.findById(100L);

        //then
        assertThat(employeeOptional).isNotPresent();
    }
}