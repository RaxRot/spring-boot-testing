package com.raxrot.sbtesting.repository;

import com.raxrot.sbtesting.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
