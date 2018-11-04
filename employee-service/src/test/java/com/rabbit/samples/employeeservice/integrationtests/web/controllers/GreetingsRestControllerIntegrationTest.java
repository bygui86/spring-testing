package com.rabbit.samples.employeeservice.integrationtests.web.controllers;

import com.rabbit.samples.employeeservice.EmployeeServiceApplication;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
		classes = EmployeeServiceApplication.class
)
public class GreetingsRestControllerIntegrationTest {

	// alternative to @Value("${server.port}"), use following
	@LocalServerPort
	int serverPort;

	String url;

	@Before
	public void before() {

		url = "http://localhost:" + serverPort + "/";
	}

	@Test
	public void givenTestRestTemplate_whenGetForEntity_thenStatusOk() {

		// given
		final TestRestTemplate testRestTemplate = new TestRestTemplate();

		// when
		final ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

		// then
		Assertions.assertThat(response.getStatusCode())
				.isEqualTo(HttpStatus.OK);
		Assertions.assertThat(response.getBody())
				.isEqualTo("Greetings from employee-service ;)");
	}

}
