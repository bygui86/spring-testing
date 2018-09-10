package com.rabbit.samples.employeeservice.contracttests;

import com.rabbit.samples.employeeservice.web.controllers.EmployeeRestController;
import com.rabbit.samples.employeeservice.web.controllers.GreetingsRestController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMessageVerifier
@DirtiesContext
public abstract class AbstractRestControllerContractTest {

	@Autowired
	private GreetingsRestController greetingsRestController;

	@Autowired
	private EmployeeRestController employeeRestController;

	@Before
	public void setup() {

		RestAssuredMockMvc.standaloneSetup(
				MockMvcBuilders.standaloneSetup(
						greetingsRestController,
						employeeRestController
				)
		);
	}

}
