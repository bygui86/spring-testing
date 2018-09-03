package com.rabbit.samples.employeeservice.integrationtests.repos;

import com.rabbit.samples.employeeservice.persistence.entities.Employee;
import com.rabbit.samples.employeeservice.persistence.repos.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

// import org.springframework.test.annotation.IfProfileValue;


/*
	Selective test running based on Spring annotation @IfProfileValue
 */
// @IfProfileValue(name = "spring.testing.profile", values = {"all", "integration"})
/*
	@RunWith is used to provide a bridge between Spring Boot test features and JUnit.
	PLEASE NOTE: SpringRunner is an alias for SpringJUnit4ClassRunner
 */
@RunWith(SpringRunner.class)
/*
	@DataJpaTest provides some standard setup needed for testing the persistence layer:
		. configuring H2, an in-memory database
		. setting Hibernate, Spring Data, and the DataSource
		. performing an @EntityScan
		. turning on SQL logging
 */
@DataJpaTest
@Transactional
public class EmployeeRepositoryIntegrationTest {

	@Autowired
	@Qualifier("employeeRepository")
	private EmployeeRepository employeeRepository;

	/*
		To carry out some DB operation, we need some records already setup in our database. To setup such data, we can use TestEntityManager.
		The TestEntityManager provided by Spring Boot is an alternative to the standard JPA EntityManager that provides methods commonly used when
		writing tests.
	 */
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
		final Employee entity = employeeRepository.findByName(name);

		// then
		Assertions.assertThat(entity.getName())
				.isEqualTo(name);
	}

}
