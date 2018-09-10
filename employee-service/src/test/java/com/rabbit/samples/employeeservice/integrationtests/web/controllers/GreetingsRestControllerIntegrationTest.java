package com.rabbit.samples.employeeservice.integrationtests.web.controllers;

import com.rabbit.samples.employeeservice.EmployeeServiceApplication;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(
		/*
			The webEnvironment attribute configures our runtime environment.
			Here we are using WebEnvironment.DEFINED_PORT, so that the container will operate as normal.

			PLEASE NOTE:
			If we would like to use a SpringBootTest.WebEnvironment.RANDOM_PORT, we can inject the randomy generated port like following
				@LocalServerPort
				private int port;
		 */
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
		classes = EmployeeServiceApplication.class
)
public class GreetingsRestControllerIntegrationTest {

	@Value("${server.port}")
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
