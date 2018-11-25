package com.rabbit.samples.nicknameservice.unittests;

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
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/*
	PLEASE NOTE:
	If in the class under test we would like to use a customised RestTemplate, we could setup the test this way:
		. comment these annotations
			@AutoConfigureWebClient(registerRestTemplate = true)
			@AutoConfigureMockRestServiceServer
		. the MockRestServiceServer is not injected anymore, so comment the @Autowired
		. define somewhere (Configuration class, directly in this test, ...) the customised RestTemplate to be used
		. create a @Before method to manually create the MockRestServiceServer assigning the customised RestTemplate
			@Before
			public void setup() {
				mockRestServiceServer = MockRestServiceServer.bindTo(outboundRestTemplate).build();
			}
 */
@RunWith(SpringRunner.class)
@AutoConfigureWebClient(registerRestTemplate = true)
@AutoConfigureMockRestServiceServer
@Import(RestTemplateNicknameServiceImpl.class)
@TestPropertySource(
		locations = {
				"classpath:application.properties",
				"classpath:application-resttemplate.properties"
		}
)
public class RestTemplateNicknameServiceUnitTest {

	@Autowired
	private MockRestServiceServer mockRestServiceServer;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	@Qualifier("nicknameService")
	private RestTemplateNicknameServiceImpl nicknameService;

	@MockBean(name = "nicknameRepository")
	private NicknameRepository nicknameRepository;

	@Value("${rest.client.protocol}")
	private String protocol;

	@Value("${rest.client.url.encoding}")
	private String urlEncoding;

	@Value("${employee.service.host}")
	private String host;

	@Value("${employee.service.port}")
	private int port;

	@After
	public void tearDown() {

		mockRestServiceServer.reset();
	}

	@Test
	public void givenEmployeeName_whenGetNickname_thenReturnNickname() throws JsonProcessingException, UnsupportedEncodingException {

		// given
		final Long employeeId = 1L;
		final String employeeName = "Robert Martin";
		final EmployeeDto employeeDto = EmployeeDto.builder().id(employeeId).name(employeeName).build();

		final Long nicknameId = 4L;
		final String nicknameStr = "Uncle Bob";
		final Nickname nickname = Nickname.builder().id(nicknameId).employeeId(employeeId).nickname(nicknameStr).build();

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

		BDDMockito
				.when(nicknameRepository.findByEmployeeId(employeeId))
				.thenReturn(nickname);

		// when
		final Nickname response = nicknameService.getByEmployeeName(employeeName);

		// then
		Assertions.assertThat(response.getId())
				.isEqualTo(nicknameId);
		Assertions.assertThat(response.getEmployeeId())
				.isEqualTo(employeeId);
		Assertions.assertThat(response.getNickname())
				.isEqualTo(nicknameStr);

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
