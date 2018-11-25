package com.rabbit.samples.employeeservice.web.configs;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@Configuration(value = "swagger2Config")
@EnableSwagger2
public class Swagger2Config {

	@Value("${swagger2.package.name:com.rabbit.samples.employeeservice}")
	String swaggerPackageName;

	@Value("${swagger2.path.selector:/employees/**}")
	String swaggerPathSelector;

	/**
	 * Swagger2 API documentation setup
	 *
	 * @return {@link Docket}
	 */
	@Bean
	public Docket api() {

		log.debug("Loading SWAGGER-2 configurations: package {} path {}",
				getSwaggerPackageName(), getSwaggerPathSelector());

		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(
						RequestHandlerSelectors.basePackage(
								getSwaggerPackageName()
						)
				)
				.paths(
						PathSelectors.ant(
								getSwaggerPathSelector()
						)
				)
				.build()
				;
	}

}
