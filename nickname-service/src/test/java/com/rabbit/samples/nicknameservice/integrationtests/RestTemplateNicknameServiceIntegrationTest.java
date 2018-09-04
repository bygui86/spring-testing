package com.rabbit.samples.nicknameservice.integrationtests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbit.samples.nicknameservice.feign.dtos.EmployeeDto;
import com.rabbit.samples.nicknameservice.persistence.entities.Nickname;
import com.rabbit.samples.nicknameservice.persistence.repos.NicknameRepository;
import com.rabbit.samples.nicknameservice.services.impl.RestTemplateNicknameServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


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
@Import({
		RestTemplateNicknameServiceImpl.class, NicknameRepository.class
})
/*
	PLEASE NOTE:
	As we are not using the @SpringBootTest annotation, if we need some test-specific properties, we have to load them manually.
 */
@TestPropertySource(
		locations = {
				"classpath:application.properties",
				"classpath:application-resttemplate.properties"
		}
)
@DataJpaTest
@Transactional
public class RestTemplateNicknameServiceIntegrationTest {

	@Autowired
	private MockRestServiceServer mockRestServiceServer;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	@Qualifier("nicknameService")
	private RestTemplateNicknameServiceImpl nicknameService;

	@Value("${rest.client.protocol}")
	private String protocol;

	@Value("${rest.client.url.encoding}")
	private String urlEncoding;

	@Value("${employee.service.host}")
	private String host;

	@Value("${employee.service.port}")
	private int port;

	@After
	public void after() {

		mockRestServiceServer.reset();
	}

	@Test
	public void givenEmployeeName_whenGetNickname_thenReturnNickname() throws JsonProcessingException, UnsupportedEncodingException {

		// given
		final Long employeeId = 1L;
		final String employeeName = "Robert Martin";
		final EmployeeDto employeeDto = EmployeeDto.builder().id(employeeId).name(employeeName).build();

		final Long nicknameId = 4L;
		final String nickname = "Uncle Bob";

		final String url = getRootUrl() + "/employees/name?name=" + URLEncoder.encode(employeeName, urlEncoding);

		mockRestServiceServer
				.expect(
						ExpectedCount.once(),
						MockRestRequestMatchers.requestTo(url)
				)
				.andExpect(
						MockRestRequestMatchers.method(HttpMethod.GET)
				)
				.andRespond(
						MockRestResponseCreators
								.withSuccess(
										convertObjectToJsonString(employeeDto),
										MediaType.APPLICATION_JSON
								)
				);

		// when
		final Nickname response = nicknameService.getByEmployeeName(employeeName);

		// then
		Assertions.assertThat(response.getId())
				.isEqualTo(nicknameId);
		Assertions.assertThat(response.getEmployeeId())
				.isEqualTo(employeeId);
		Assertions.assertThat(response.getNickname())
				.isEqualTo(nickname);

		// verify
		mockRestServiceServer.verify();
	}

	private String convertObjectToJsonString(final Object obj) throws JsonProcessingException {

		return objectMapper.writeValueAsString(obj);
	}

	private String getRootUrl() {

		return protocol + "://" + host + ":" + port;
	}

}
