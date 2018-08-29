package com.rabbit.samples.employeeservice.web.controllers;

import com.rabbit.samples.employeeservice.services.EmployeeService;
import com.rabbit.samples.employeeservice.web.dtos.EmployeeDto;
import com.rabbit.samples.employeeservice.web.mappers.EmployeeMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@RestController("employeeRestController")
@RequestMapping("/employees")
public class EmployeeRestController {

	@Autowired
	@Qualifier("employeeService")
	EmployeeService employeeService;

	@Autowired
	EmployeeMapper employeeMapper;

	@GetMapping
	public List<EmployeeDto> getAll() {

		log.debug("get all employees...");

		return getEmployeeService().getAll()
				.stream()
				.map(getEmployeeMapper()::mapEntityToDto)
				.collect(Collectors.toList());
	}

	@GetMapping("/{name}")
	public EmployeeDto getByName(
			@PathVariable final String name) {

		log.debug("get employee for name {}...", name);

		return getEmployeeMapper().mapEntityToDto(
				getEmployeeService().getByName(name)
		);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EmployeeDto create(
			@RequestBody final EmployeeDto newEmployeeDto) {

		log.debug("new newEmployee {}...", newEmployeeDto);

		return getEmployeeMapper().mapEntityToDto(
				getEmployeeService().save(
						getEmployeeMapper().mapDtoToEntity(newEmployeeDto)
				)
		);
	}

}
