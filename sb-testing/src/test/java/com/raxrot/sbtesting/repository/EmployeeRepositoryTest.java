package com.raxrot.sbtesting.repository;

import com.raxrot.sbtesting.entity.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("save employee")
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

}