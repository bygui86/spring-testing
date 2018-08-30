package com.rabbit.samples.employeeservice.unittests.services;

import com.rabbit.samples.employeeservice.persistence.entities.Employee;
import com.rabbit.samples.employeeservice.persistence.repos.EmployeeRepository;
import com.rabbit.samples.employeeservice.services.EmployeeService;
import com.rabbit.samples.employeeservice.services.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceUnitTest {

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
