package com.raxrot.sbtesting.repository;

import com.raxrot.sbtesting.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    @Query("select e from Employee e where e.firstName=:firstName and e.lastName=:lastName")
    Employee findByJPQL(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
