package com.rabbit.samples.nicknameservice.contracttests;

import com.rabbit.samples.nicknameservice.feign.dtos.EmployeeDto;
import com.rabbit.samples.nicknameservice.utils.UrlUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


/*
	PLEASE NOTE:
	This test is not working using org.springframework.test.web.servlet.MockMvc instead of org.springframework.web.client.RestTemplate
 */
@RunWith(SpringRunner.class)
@AutoConfigureWebClient(registerRestTemplate = true)
@AutoConfigureStubRunner(
		ids = "com.rabbit.samples:employee-service:+:stubs:8082",
		/*
			This enum indicates from where to take the stubs.

			PLEASE NOTE:
			. spring-cloud-contract 1.2.x > workOffline = true
			. spring-cloud-contract 2.0.x > stubsMode = StubRunnerProperties.StubsMode.LOCAL
		 */
		stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
@TestPropertySource(
		locations = {
				"classpath:application.properties",
				"classpath:application-resttemplate.properties"
		}
)
public class EmployeeServiceContractTest {

	/*
		Alternative to @AutoConfigureStubRunner annotation
	 */
	// @Rule
	// public StubRunnerRule stubRunnerRule = new StubRunnerRule()
	// 		.downloadStub("com.rabbit.samples", "employee-service", "+", "stubs")
	// 		.withPort(8082)
	// 		.stubsMode(StubRunnerProperties.StubsMode.LOCAL);

	@Autowired
	RestTemplate restTemplate;

	@Test
	public void givenEmployeeName_WhenCallingEmployeeService_ThenReturnEmployeeDto() {

		// given
		final String employeeName = "Robert Martin";

		// when
		final EmployeeDto employeeDto = restTemplate.getForObject(
				UriComponentsBuilder
						.fromUriString("http://localhost:8082/employees/name")
						.queryParam("name", UrlUtils.urlEncodeString(employeeName, "UTF-8"))
						.buildAndExpand()
						.toUri(),
				EmployeeDto.class
		);

		// then
		Assertions.assertThat(employeeDto)
				.isNotNull();
		Assertions.assertThat(employeeDto.getId())
				.isNotNull();
		Assertions.assertThat(employeeDto.getName())
				.isEqualTo(employeeName);
	}

}
