package com.rabbit.samples.employeeservice.functionaltests;

import com.rabbit.samples.employeeservice.constants.ProfileConstants;
import com.rabbit.samples.employeeservice.persistence.entities.Employee;
import com.rabbit.samples.employeeservice.persistence.repos.EmployeeRepository;
import com.rabbit.samples.employeeservice.web.dtos.EmployeeDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(ProfileConstants.SPRING_PROFILE_TEST)
@DirtiesContext
public class EmployeeRestControllerFunctionalTest {

	@LocalServerPort
	private int port;

	@Autowired
	private EmployeeRepository employeeRepository;

	private final String endpoint = "/employees";

	@Before
	public void setUp() {

		// reset DB
		employeeRepository.deleteAll();

		RestAssured.port = port;
	}

	@After
	public void tearDown() {

		// reset DB
		employeeRepository.deleteAll();
	}

	@Test
	public void givenEmployees_whenGetAll_thenReturnEmployess() {

		// given
		final Long alexId = 1L;
		final String alexName = "alex";
		final Long bobId = 2L;
		final String bobName = "bob";
		createEmployee(alexId, alexName);
		createEmployee(bobId, bobName);

		final List<EmployeeDto> employeeDtos =
				RestAssured
						.given()
						.accept(ContentType.JSON)

						.when()
						.get(endpoint)

						.then()
						.statusCode(HttpStatus.OK.value())
						.extract()
						.jsonPath().getList(".", EmployeeDto.class);

		Assertions.assertThat(employeeDtos).hasSize(2);
		Assertions.assertThat(employeeDtos.get(0).getName()).isEqualTo(alexName);
		Assertions.assertThat(employeeDtos.get(1).getName()).isEqualTo(bobName);
	}

	@Test
	public void givenEmployee_whenPost_thenCreateAndReturnEmployee() throws Exception {

		// given
		final String name = "alex";
		final EmployeeDto alexDto = EmployeeDto.builder().name(name).build();

		final EmployeeDto employeeDto = RestAssured
				.given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(alexDto)

				.when()
				.post(endpoint)

				.then()
				.statusCode(HttpStatus.CREATED.value())
				.extract()
				.jsonPath().getObject(".", EmployeeDto.class);

		Assertions.assertThat(employeeDto.getName()).isEqualTo(name);
	}

	private void createEmployee(final Long id, final String name) {

		employeeRepository.saveAndFlush(
				Employee.builder().id(id).name(name).build()
		);
	}

}
