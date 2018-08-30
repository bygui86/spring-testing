package com.rabbit.samples.employeeservice.services.impl;

import com.rabbit.samples.employeeservice.persistence.entities.Employee;
import com.rabbit.samples.employeeservice.persistence.repos.EmployeeRepository;
import com.rabbit.samples.employeeservice.services.EmployeeService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	@Qualifier("employeeRepository")
	EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getAll() {

		log.debug("get all employees...");

		return getEmployeeRepository().findAll();
	}

	@Override
	public Employee getById(final Long id) {

		log.debug("get employee by id {}...", id);

		return getEmployeeRepository()
				.findById(id)
				.orElse(createEmptyEmployee());
	}

	@Override
	public Employee getByName(final String name) {

		log.debug("get employee by name {}...", name);

		final Employee employee = getEmployeeRepository().findByName(name);
		if (employee == null) {
			return createEmptyEmployee();
		}
		return employee;
	}

	@Override
	public Employee save(final Employee employee) {

		log.debug("post new employee {}...", employee);

		return getEmployeeRepository().save(employee);
	}

	protected Employee createEmptyEmployee() {

		return Employee.builder().build();
	}

}
