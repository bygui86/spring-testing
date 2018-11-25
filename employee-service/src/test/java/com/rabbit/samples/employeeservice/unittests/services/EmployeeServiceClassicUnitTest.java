package com.rabbit.samples.employeeservice.unittests.services;

import com.rabbit.samples.employeeservice.persistence.entities.Employee;
import com.rabbit.samples.employeeservice.persistence.repos.EmployeeRepository;
import com.rabbit.samples.employeeservice.services.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.MockitoJUnitRunner;


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
		BDDMockito.when(employeeRepository.findByName(name))
				.thenReturn(Employee.builder().name(name).build());

		// when
		final Employee found = employeeService.getByName(name);

		// then
		Assertions.assertThat(found.getName())
				.isEqualTo(name);

		// verify
		BDDMockito
				.verify(employeeRepository, VerificationModeFactory.times(1))
				.findByName(name);
	}

}