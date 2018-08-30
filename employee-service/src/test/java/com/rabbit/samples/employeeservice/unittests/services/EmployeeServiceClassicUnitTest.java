package com.rabbit.samples.employeeservice.unittests.services;

import com.rabbit.samples.employeeservice.persistence.entities.Employee;
import com.rabbit.samples.employeeservice.persistence.repos.EmployeeRepository;
import com.rabbit.samples.employeeservice.services.impl.EmployeeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.when;


@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceClassicUnitTest {

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@Mock
	private EmployeeRepository employeeRepository;

	final String name = "alex";

	@Test
	public void givenValidName_thenReturnFoundEmployee() {

		// given
		when(employeeRepository.findByName(name))
				.thenReturn(Employee.builder().name(name).build());

		// when
		final Employee found = employeeService.getByName(name);

		// then
		assertThat(name).isEqualTo(found.getName());

		// verify
		verify(employeeRepository, VerificationModeFactory.times(1)).findByName(name);
	}

}