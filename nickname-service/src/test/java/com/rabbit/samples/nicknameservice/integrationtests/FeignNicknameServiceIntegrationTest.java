package com.rabbit.samples.nicknameservice.integrationtests;

import com.rabbit.samples.nicknameservice.feign.clients.EmployeeServiceClient;
import com.rabbit.samples.nicknameservice.feign.dtos.EmployeeDto;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.ribbon.FeignRibbonClientAutoConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@RunWith(SpringRunner.class)
@ImportAutoConfiguration({
		RibbonAutoConfiguration.class,
		FeignRibbonClientAutoConfiguration.class,
		FeignAutoConfiguration.class,
		HttpMessageConvertersAutoConfiguration.class
})
@EnableFeignClients(basePackages = "com.rabbit.samples.nicknameservice.feign.clients")
@TestPropertySource(
		locations = {
				"classpath:application.properties",
				"classpath:application-feign.properties"
		}
)
public class FeignNicknameServiceIntegrationTest {

	@Autowired
	@Qualifier("employeeServiceClient")
	private EmployeeServiceClient employeeServiceClient;

	/*
		PLEASE NOTE:
		we can use either ClientAndServer or MockServer + MockServerClient
	 */
	private ClientAndServer clientAndServer;

	// private MockServer mockServer;
	// private MockServerClient mockServerClient;

	@Value("${rest.client.url.encoding}")
	private String urlEncoding;

	@Value("${employee.service.host}")
	private String host;

	@Value("${employee.service.port}")
	private int port;

	@Before
	public void setUp() {

		clientAndServer = ClientAndServer.startClientAndServer(port);

		// mockServer = new MockServer(port);
		// mockServerClient = new MockServerClient(host, port);
	}

	@After
	public void tearDown() {

		clientAndServer.stop();

		// mockServer.stop();
		// mockServerClient.stop();
	}

	@Test
	public void givenEmployeeName_whenGetEmployee_thenReturnEmployee() throws UnsupportedEncodingException {

		// given
		final String employeeName = "Robert Martin";

		final HttpRequest httpRequest = createHttpRequest(employeeName);
		final HttpResponse httpResponse = createHttpResponse(employeeName);

		clientAndServer
				.when(httpRequest)
				.respond(httpResponse);

		// mockServerClient
		// 		.when(httpRequest)
		// 		.respond(httpResponse);

		// when
		final EmployeeDto employeeDto = employeeServiceClient
				.getEmployeeByName(
						URLEncoder.encode(employeeName, urlEncoding)
				);

		// then
		Assertions.assertThat(employeeDto.getName())
				.isEqualTo(employeeName);

		// verify
		clientAndServer.verify(httpRequest, VerificationTimes.once());
		clientAndServer.reset();

		// mockServerClient.verify(httpRequest, VerificationTimes.once());
		// mockServerClient.reset();
	}

	private HttpRequest createHttpRequest(final String employeeName) throws UnsupportedEncodingException {

		return HttpRequest
				.request()
				.withMethod("GET")
				.withPath("/employees/name")
				// .withHeader("Accept", "application/json") // TODO seems that feign annotation @Headers is not working properly
				.withQueryStringParameter("name", URLEncoder.encode(employeeName, urlEncoding))
				;
	}

	private HttpResponse createHttpResponse(final String employeeName) {

		final EmployeeDto employeeDto = EmployeeDto.builder().name(employeeName).build();

		return HttpResponse
				.response()
				.withStatusCode(200)
				.withHeader(Header.header("Content-Type", "application/json"))
				.withBody(JsonBody.json(employeeDto))
				;
	}

}
