package com.rabbit.samples.employeeservice.unittests.services;

import com.rabbit.samples.employeeservice.persistence.entities.Employee;
import com.rabbit.samples.employeeservice.persistence.repos.EmployeeRepository;
import com.rabbit.samples.employeeservice.services.EmployeeService;
import com.rabbit.samples.employeeservice.services.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
/*
	To check the Service class, we need to have an instance of Service class created and available as a @Bean so that we can @Autowire it in our test class.
	This configuration is achieved by using the @TestConfiguration annotation.

	PLEASE NOTE:
	During component scanning, we might find components or configurations created only for specific tests accidentally get picked up everywhere.
	To help prevent that, Spring Boot provides @TestConfiguration annotation that can be used on classes in src/test/java to indicate that they should
	not be picked up by scanning.
 */
@Import(EmployeeServiceImpl.class)
public class EmployeeServiceSpringUnitTest {

	@Autowired
	private EmployeeService employeeService;

	/*
		Mocking support provided by Spring Boot Test: it creates a Mock for the EmployeeRepository which can be used to bypass the call to the actual
		EmployeeRepository.

		PLEASE NOTE:
		In the EmployeeService is specified a @Qualifier for the EmployeeRepository injection, that refers to a bean named "employeeRepository".
		During the test execution, the EmployeeRepository bean is mocked with a name different than the variable name (something like
		com.rabbit.samples.employeeservice.unittests.services.EmployeeRepository#0), leading to miss the EmployeeService injection.
	 */
	@MockBean(name = "employeeRepository")
	private EmployeeRepository employeeRepository;

	final String name = "alex";

	@Test
	public void givenValidName_thenReturnFoundEmployee() throws Exception {

		// given
		BDDMockito
				.when(employeeRepository.findByName(name))
				.thenReturn(Employee.builder().name(name).build());

		// when
		final Employee found = employeeService.getByName(name);

		// then
		Assertions.assertThat(found.getName())
				.isEqualTo(name);

		// verify
		BDDMockito.verify(employeeRepository, VerificationModeFactory.times(1)).findByName(name);
		BDDMockito.reset(employeeRepository);
	}

}
