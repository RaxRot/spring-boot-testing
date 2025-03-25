package com.raxrot.springboot.repository;

import com.raxrot.springboot.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    //Define custom JPQL query with index params
    @Query("select e from Employee e where e.firstName=?1 and e.lastName=?2")
    Employee findEmployeeJPQL(String firstName, String lastName);

    @Query("select e from Employee e where e.firstName=:firstName and e.lastName=:lastName")
    Employee findEmployeeJPQLNamedParameters(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query(nativeQuery = true, value = "SELECT * FROM employees e WHERE e.first_name = :firstName AND e.last_name = :lastName")
    Employee findEmployeeNativeSqlNamed(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
