package com.rabbit.samples.employeeservice.persistence.repos;

import com.rabbit.samples.employeeservice.persistence.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("employeeRepository")
// @Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Employee findByName(final String name);

}
