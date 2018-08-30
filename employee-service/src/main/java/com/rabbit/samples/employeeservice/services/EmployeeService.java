package com.rabbit.samples.employeeservice.services;

import com.rabbit.samples.employeeservice.persistence.entities.Employee;

import java.util.List;


public interface EmployeeService {

	List<Employee> getAll();

	Employee getById(final Long id);

	Employee getByName(final String name);

	Employee save(final Employee employee);

}
