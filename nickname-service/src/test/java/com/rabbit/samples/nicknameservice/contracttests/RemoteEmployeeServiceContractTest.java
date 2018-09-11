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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


/*
	PLEASE NOTE:
	This test is not working using org.springframework.test.web.servlet.MockMvc instead of org.springframework.web.client.RestTemplate
 */
@RunWith(SpringRunner.class)
@AutoConfigureWebClient(registerRestTemplate = true)
@AutoConfigureStubRunner(
		/*
			This enum indicates from where to take the stubs.

			PLEASE NOTE:
			. spring-cloud-contract 1.2.x > workOffline = true
			. spring-cloud-contract 2.0.x > stubsMode = StubRunnerProperties.StubsMode.LOCAL
		 */
		stubsMode = StubRunnerProperties.StubsMode.REMOTE,
		repositoryRoot = "git://git@github.com:bygui86/spring-testing-contracts-stubs.git",
		/*
			PLEASE NOTE:
			Here we must specify a version
		 */
		ids = "com.rabbit.samples:employee-service:0.0.1:stubs:8082"
)
@TestPropertySource(
		locations = {
				"classpath:application.properties"
		}
)
public class RemoteEmployeeServiceContractTest {

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


	private static final String EMPLOYEE_SERVICE_ROOT_URL = "http://localhost:8082/employees";

	private static final String CHAR_ENCODING = "UTF-8";

	@Test
	public void whenCallingEmployeeService_thenReturnEmployeeList() {

		// given
		// -

		// when
		final ResponseEntity<List<EmployeeDto>> response = restTemplate.exchange(
				UriComponentsBuilder
						.fromUriString(EMPLOYEE_SERVICE_ROOT_URL)
						.buildAndExpand()
						.toUri(),
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<EmployeeDto>>() {
					// no-op
				}
		);
		final List<EmployeeDto> employeeDtos = response.getBody();

		// then
		Assertions.assertThat(employeeDtos)
				.isNotNull()
				.isNotEmpty();
		Assertions.assertThat(employeeDtos.size())
				.isEqualTo(4);
	}

	@Test
	public void givenEmployeeId_whenCallingEmployeeService_thenReturnEmployee() {

		// given
		final Long employeeId = 2L;
		final String expectedEmployeeName = "Martin Fowler";

		// when
		final EmployeeDto employeeDto = restTemplate.getForObject(
				UriComponentsBuilder
						.fromUriString(EMPLOYEE_SERVICE_ROOT_URL + "/id")
						.queryParam("id", employeeId)
						.buildAndExpand()
						.toUri(),
				EmployeeDto.class
		);

		// then
		Assertions.assertThat(employeeDto)
				.isNotNull();
		Assertions.assertThat(employeeDto.getId())
				.isEqualTo(employeeId);
		Assertions.assertThat(employeeDto.getName())
				.isEqualTo(expectedEmployeeName);
	}

	@Test
	public void givenEmployeeName_whenCallingEmployeeService_thenReturnEmployee() {

		// given
		final String employeeName = "Robert Martin";
		final Long expectedEmployeeId = 1L;

		// when
		final EmployeeDto employeeDto = restTemplate.getForObject(
				UriComponentsBuilder
						.fromUriString(EMPLOYEE_SERVICE_ROOT_URL + "/name")
						.queryParam("name", UrlUtils.urlEncodeString(employeeName, CHAR_ENCODING))
						.buildAndExpand()
						.toUri(),
				EmployeeDto.class
		);

		// then
		Assertions.assertThat(employeeDto)
				.isNotNull();
		Assertions.assertThat(employeeDto.getId())
				.isEqualTo(expectedEmployeeId);
		Assertions.assertThat(employeeDto.getName())
				.isEqualTo(employeeName);
	}

	@Test
	public void givenEmployee_whenCallingEmployeeService_thenReturnCreatedEmployee() {

		// given
		final String employeeName = "James Gosling";

		// when
		final EmployeeDto requestEmployeeDto = EmployeeDto.builder().name(employeeName).build();
		final EmployeeDto responseEmployeeDto = restTemplate.postForObject(
				UriComponentsBuilder
						.fromUriString(EMPLOYEE_SERVICE_ROOT_URL)
						.buildAndExpand()
						.toUri(),
				requestEmployeeDto,
				EmployeeDto.class
		);

		// then
		Assertions.assertThat(responseEmployeeDto)
				.isNotNull();
		Assertions.assertThat(responseEmployeeDto.getId())
				.isNotNull();
		Assertions.assertThat(responseEmployeeDto.getName())
				.isEqualTo(employeeName);
	}

}
