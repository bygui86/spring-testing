package com.rabbit.samples.employeeservice.integrationtests.services;

import com.rabbit.samples.employeeservice.persistence.entities.Employee;
import com.rabbit.samples.employeeservice.services.EmployeeService;
import com.rabbit.samples.employeeservice.services.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@Import(EmployeeServiceImpl.class)
@DataJpaTest
@Transactional
public class EmployeeServiceIntegrationTest {

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
		Assertions.assertThat(entity.getName())
				.isEqualTo(name);
	}

}
