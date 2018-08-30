package com.rabbit.samples.employeeservice.integrationtests.web.controllers;

import com.rabbit.samples.employeeservice.EmployeeServiceApplication;
import com.rabbit.samples.employeeservice.persistence.entities.Employee;
import com.rabbit.samples.employeeservice.persistence.repos.EmployeeRepository;
import com.rabbit.samples.employeeservice.utils.JsonUtil;
import com.rabbit.samples.employeeservice.web.dtos.EmployeeDto;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@RunWith(SpringRunner.class)
/*
	The @SpringBootTest annotation can be used when we need to bootstrap the entire container. The annotation works by creating the ApplicationContext
	that will be utilized in our tests.
 */
@SpringBootTest(
		/*
			The webEnvironment attribute configures our runtime environment.
			Here we are using WebEnvironment.MOCK, so that the container will operate in a mock servlet environment.
		 */
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = EmployeeServiceApplication.class
)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
/*
	We can use the @TestPropertySource annotation to configure locations of properties files specific to our tests.

	PLEASE NOTE:
	The property file loaded with @TestPropertySource will override the existing application.properties file.
 */
// @TestPropertySource(locations = "classpath:application-test.properties")
public class EmployeeRestControllerIntegrationTest {

	/*
		PLEASE NOTE:
		The difference from the Controller layer unit tests is that here nothing is mocked and end-to-end scenarios will be executed.
	 */

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Before
	public void before() {

		// reset DB
		employeeRepository.deleteAll();
	}

	@After
	public void after() {

		// reset DB
		employeeRepository.deleteAll();
	}

	@Test
	public void givenEmployees_whenGetAll_thenReturnEmployess() throws Exception {

		// given
		final Long alexId = 1L;
		final String alexName = "alex";
		final Long bobId = 2L;
		final String bobName = "bob";
		createEmployee(alexId, alexName);
		createEmployee(bobId, bobName);

		mockMvc
				// when
				.perform(
						MockMvcRequestBuilders
								.get("/employees")
								.accept(MediaType.APPLICATION_JSON)
				)

				// then
				.andExpect(
						MockMvcResultMatchers.status().isOk()
				)
				.andExpect(
						MockMvcResultMatchers
								.content()
								.contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2))
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$[0]", Matchers.notNullValue())
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is(alexName))
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$[1]", Matchers.notNullValue())
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$[1].name", CoreMatchers.is(bobName))
				)
		;

	}

	@Test
	public void givenEmployee_whenPost_thenCreateAndReturnEmployee() throws Exception {

		// given
		final String name = "alex";
		final EmployeeDto alexDto = EmployeeDto.builder().name(name).build();

		mockMvc
				// when
				.perform(
						MockMvcRequestBuilders
								.post("/employees")
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.content(JsonUtil.toJson(alexDto))
				)

				// then
				.andExpect(
						MockMvcResultMatchers.status().isCreated()
				)
				.andExpect(
						MockMvcResultMatchers
								.content()
								.contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue())
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.name", Matchers.is(name))
				)
		;
	}

	private void createEmployee(final Long id, final String name) {

		employeeRepository.saveAndFlush(
				Employee.builder().id(id).name(name).build()
		);
	}

}
