package com.raxrot.sbtesting.service;

import com.raxrot.sbtesting.dto.EmployeeDTO;
import com.raxrot.sbtesting.entity.Employee;
import com.raxrot.sbtesting.exception.ApiException;
import com.raxrot.sbtesting.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private ModelMapper modelMapper;
    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1L)
                .firstName("vlad")
                .lastName("bulahov")
                .email("vlad@gmail.com")
                .build();

        employeeDTO = EmployeeDTO.builder()
                .id(1L)
                .firstName("vlad")
                .lastName("bulahov")
                .email("vlad@gmail.com")
                .build();
    }

    @Test
    @DisplayName("Save employee")
    void givenEmployee_whenSaveEmployee_thenReturnEmployeeObject() {
        // given
        BDDMockito.given(modelMapper.map(employeeDTO, Employee.class)).willReturn(employee);
        BDDMockito.given(modelMapper.map(employee, EmployeeDTO.class)).willReturn(employeeDTO);
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        // when
        EmployeeDTO savedDto = employeeService.saveEmployee(employeeDTO);

        // then
        assertThat(savedDto).isNotNull();
        assertThat(savedDto.getEmail()).isEqualTo("vlad@gmail.com");
    }

    @Test
    @DisplayName("Save employee that throws")
    void givenEmployee_whenSaveEmployee_thenThrowsException() {
        // given
        BDDMockito.given(employeeRepository.findByEmail(employeeDTO.getEmail()))
                .willReturn(Optional.of(employee));

        // when + then
        assertThrows(ApiException.class, () -> employeeService.saveEmployee(employeeDTO));
    }

    @Test
    @DisplayName("Get all employees")
    void givenEmployees_whenGetAllEmployees_thenReturnEmployeeDTOList() {
        // given — сущности
        Employee employee1 = Employee.builder()
                .id(1L)
                .firstName("Vlad")
                .lastName("Bulahov")
                .email("vlad@gmail.com")
                .build();

        Employee employee2 = Employee.builder()
                .id(2L)
                .firstName("Anna")
                .lastName("Ivanova")
                .email("anna@gmail.com")
                .build();

        List<Employee> employeeList = List.of(employee1, employee2);

        EmployeeDTO dto1 = EmployeeDTO.builder()
                .id(1L)
                .firstName("Vlad")
                .lastName("Bulahov")
                .email("vlad@gmail.com")
                .build();

        EmployeeDTO dto2 = EmployeeDTO.builder()
                .id(2L)
                .firstName("Anna")
                .lastName("Ivanova")
                .email("anna@gmail.com")
                .build();

        BDDMockito.given(employeeRepository.findAll()).willReturn(employeeList);
        BDDMockito.given(modelMapper.map(employee1, EmployeeDTO.class)).willReturn(dto1);
        BDDMockito.given(modelMapper.map(employee2, EmployeeDTO.class)).willReturn(dto2);

        // when
        List<EmployeeDTO> result = employeeService.getAllEmployees();

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getEmail()).isEqualTo("vlad@gmail.com");
        assertThat(result.get(1).getEmail()).isEqualTo("anna@gmail.com");
    }

    @Test
    @DisplayName("Get employee by id")
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeDTO() {
        // given

        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Vlad")
                .lastName("Bulahov")
                .email("vlad@gmail.com")
                .build();

        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .id(1L)
                .firstName("Vlad")
                .lastName("Bulahov")
                .email("vlad@gmail.com")
                .build();

        BDDMockito.given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        BDDMockito.given(modelMapper.map(employee, EmployeeDTO.class)).willReturn(employeeDTO);

        // when
        EmployeeDTO result = employeeService.getEmployeeById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("vlad@gmail.com");
    }

    @Test
    @DisplayName("Update employee")
    void givenEmployeeDTO_whenUpdateEmployee_thenReturnUpdatedDTO() {
        // given

        Employee existingEmployee = Employee.builder()
                .id(1L)
                .firstName("Old")
                .lastName("Name")
                .email("old@mail.com")
                .build();

        EmployeeDTO updatedDTO = EmployeeDTO.builder()
                .firstName("New")
                .lastName("Updated")
                .email("new@mail.com")
                .build();

        Employee updatedEntity = Employee.builder()
                .id(1L)
                .firstName("New")
                .lastName("Updated")
                .email("new@mail.com")
                .build();

        BDDMockito.given(employeeRepository.findById(1L)).willReturn(Optional.of(existingEmployee));
        BDDMockito.given(employeeRepository.save(existingEmployee)).willReturn(updatedEntity);
        BDDMockito.given(modelMapper.map(updatedEntity, EmployeeDTO.class)).willReturn(updatedDTO);

        // when
        EmployeeDTO result = employeeService.updateEmployee(1L, updatedDTO);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("New");
        assertThat(result.getEmail()).isEqualTo("new@mail.com");
    }

    @Test
    @DisplayName("Delete employee")
    void givenEmployeeId_whenDeleteEmployee_thenVoid() {
        // given
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("ToDelete")
                .email("delete@mail.com")
                .build();

        BDDMockito.given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        BDDMockito.willDoNothing().given(employeeRepository).delete(employee);

        // when
        employeeService.deleteEmployee(1L);

        // then
        BDDMockito.then(employeeRepository).should().delete(employee);
    }

}