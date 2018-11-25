package com.rabbit.samples.employeeservice.unittests.configs;

import com.rabbit.samples.employeeservice.web.configs.Swagger2Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import springfox.documentation.spring.web.plugins.Docket;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 19 Sep 2018
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Swagger2Config.class)
public class Swagger2ConfigUnitTest {

	@Autowired
	ApplicationContext applicationContext;

	@Test
	public void testSwagger2Config() {

		// given
		// -

		// when
		applicationContext.getBean(Swagger2Config.class);
		final Docket swaggerDocket = applicationContext.getBean(Docket.class);

		// then
		assertThat(swaggerDocket.isEnabled())
				.isTrue();
	}

}
