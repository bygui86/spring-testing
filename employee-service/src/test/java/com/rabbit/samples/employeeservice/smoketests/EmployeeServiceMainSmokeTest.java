package com.rabbit.samples.employeeservice.smoketests;

import com.rabbit.samples.employeeservice.EmployeeServiceApplication;
import org.junit.Test;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 19 Sep 2018
 * <p>
 * PLEASE NOTE (MatteoBaiguini): Here there is no need of @RunWith because neither injections nor application context are needed.
 */
public class EmployeeServiceMainSmokeTest {

	@Test
	public void testApplicationMain() {

		// given
		final String[] applicationPropertiesArgs = {
				"--management.server.port = 65020",
				"--server.port = 65010",
				"--spring.profiles.active = test",
				"--spring.main.banner-mode = off"
		};

		// when
		EmployeeServiceApplication.main(applicationPropertiesArgs);

		// then
		// -
	}

}
