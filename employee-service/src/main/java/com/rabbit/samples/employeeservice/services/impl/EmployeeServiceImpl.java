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

import java.util.List;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@Service("employeeService")
// @Transactional
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
	public Employee getByName(final String name) {

		log.debug("get employee for name {}...", name);

		return getEmployeeRepository().findByName(name);
	}

	@Override
	public Employee save(final Employee employee) {

		return getEmployeeRepository().save(employee);
	}

}
