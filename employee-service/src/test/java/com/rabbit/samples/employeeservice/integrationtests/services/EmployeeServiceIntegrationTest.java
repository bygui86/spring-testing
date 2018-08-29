package com.rabbit.samples.employeeservice.integrationtests.services;

import com.rabbit.samples.employeeservice.persistence.entities.Employee;
import com.rabbit.samples.employeeservice.services.EmployeeService;
import com.rabbit.samples.employeeservice.services.impl.EmployeeServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@IfProfileValue(name = "spring.testing.profile", values = {"all", "integration"})
@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class EmployeeServiceIntegrationTest {

	@TestConfiguration
	@Import({
			EmployeeServiceImpl.class
	})
	static class EmployeeServiceUnitTestContextConfiguration {

		// no-op
	}


	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private TestEntityManager entityManager;

	final String name = "alex";

	@Before
	public void before() {

		entityManager.persist(Employee.builder().name(name).build());
		entityManager.flush();
	}

	@Test
	public void givenName_thenReturnEmployee() {

		// given
		// -

		// when
		final Employee entity = employeeService.getByName(name);

		// then
		Assert.assertEquals(name, entity.getName());
	}

}
