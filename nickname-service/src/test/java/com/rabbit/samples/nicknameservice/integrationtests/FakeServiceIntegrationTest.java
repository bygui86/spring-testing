package com.rabbit.samples.nicknameservice.integrationtests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbit.samples.nicknameservice.feign.dtos.EmployeeDto;
import com.rabbit.samples.nicknameservice.persistence.entities.Nickname;
import com.rabbit.samples.nicknameservice.services.impl.FakeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;


/**
 * This is just a test to verify different scenarios.
 */
@RunWith(SpringRunner.class)
/*
	@RestClientTest is giving us the possibility to test a REST communication from the client-side.

	PLEASE NOTE:
	. @RestClientTest is working just if the REST communication is consumed through RestTemplate.
	. This test works also with @SpringBootTest, but this is loading the whole ApplicationContext not needed in this case.
 */
// @RestClientTest({FakeServiceImpl.class, RestTemplateConfig.class})
/*
	These configuration annotations are taken from @RestClientTest, which is loading too many objects not needed in this test.
 */
@AutoConfigureWebClient(registerRestTemplate = true)
@AutoConfigureMockRestServiceServer
@Import(FakeServiceImpl.class)
/*
	PLEASE NOTE:
	As we are not using the @SpringBootTest annotation, if we need some test-specific properties, we have to load them manually.
 */
@TestPropertySource(locations = "classpath:application.properties")
public class FakeServiceIntegrationTest {

	@Autowired
	private MockRestServiceServer mockRestServiceServer;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	@Qualifier("fakeService")
	private FakeServiceImpl fakeService;

	@Value("${rest.client.protocol}")
	private String protocol;

	@Value("${employee.service.root.url}")
	private String rootUrl;

	@Test
	public void testGetFakeNickname() throws JsonProcessingException {

		// given
		final String url = getRootUrl() + "/fakenick";

		final String name = "Robert Martin";
		final EmployeeDto employeeDto = EmployeeDto.builder().name(name).build();

		mockRestServiceServer
				.expect(
						ExpectedCount.once(),
						MockRestRequestMatchers.requestTo(url)
				)
				.andExpect(
						MockRestRequestMatchers.method(HttpMethod.GET)
				)
				.andRespond(
						MockRestResponseCreators.withSuccess(
								objectMapper.writeValueAsString(employeeDto),
								MediaType.APPLICATION_JSON
						)
				)
		;

		// when
		final Nickname response = fakeService.getFakeNickname(name);

		// then
		Assertions.assertThat(response.getNickname())
				.isEqualTo("Uncle Bob");

		// verify
		mockRestServiceServer.verify();
	}

	@Test
	public void testPostFakeString() {

		// given
		// final String url = "http://localhost/add-comment";
		final String url = getRootUrl() + "/add-comment";
		final String jsonResponseBody = "{post : 'success'}";
		mockRestServiceServer
				.expect(
						ExpectedCount.once(),
						MockRestRequestMatchers.requestTo(url)
				)
				.andExpect(
						MockRestRequestMatchers.method(HttpMethod.POST)
				)
				.andRespond(
						MockRestResponseCreators.withSuccess(
								jsonResponseBody,
								MediaType.APPLICATION_JSON
						)
				)
		;

		// when
		final String response = fakeService.postFakeString("test post");

		// then
		Assertions.assertThat(response)
				.isEqualTo(jsonResponseBody);

		// verify
		mockRestServiceServer.verify();
	}

	@Test
	public void testGetFakeString() {

		// given
		final String url = getRootUrl();
		final String jsonResponseBody = "{message : 'under test'}";

		mockRestServiceServer
				.expect(
						ExpectedCount.once(),
						MockRestRequestMatchers.requestTo(url)
				)
				.andExpect(
						MockRestRequestMatchers.method(HttpMethod.GET)
				)
				.andRespond(
						MockRestResponseCreators.withSuccess(
								jsonResponseBody,
								MediaType.APPLICATION_JSON
						)
				)
		;

		// when
		final String response = fakeService.getFakeString();

		// then
		Assertions.assertThat(response)
				.isEqualTo(jsonResponseBody);

		// verify
		mockRestServiceServer.verify();
	}

	private String getRootUrl() {

		return protocol + "://" + rootUrl;
	}

}
