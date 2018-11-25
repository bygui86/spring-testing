package com.rabbit.samples.employeeservice.smoketests;

import com.rabbit.samples.employeeservice.constants.ProfileConstants;
import com.rabbit.samples.employeeservice.web.controllers.EmployeeRestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 19 Sep 2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(ProfileConstants.SPRING_PROFILE_TEST)
public class EmployeeServiceApplicationSmokeTest {

	@Autowired
	private ApplicationContext applicationContext;

	/*
		PLEASE NOTE (MatteoBaiguini):
		Here we are verifying just the controller spring bean, because if it is properly instantiated means that all its dependencies are satisfied.
	 */
	@Test
	public void testApplicationStartup() {

		// given
		final String employeeRestControllerBeanName = "employeeRestController";

		// when
		final Object employeeRestControllerObject = applicationContext.getBean(employeeRestControllerBeanName);

		// then
		assertThat(employeeRestControllerObject)
				.isInstanceOf(EmployeeRestController.class);
	}

}
