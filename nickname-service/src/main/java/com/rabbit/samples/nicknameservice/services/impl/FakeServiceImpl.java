package com.rabbit.samples.nicknameservice.services.impl;

import com.rabbit.samples.nicknameservice.feign.dtos.EmployeeDto;
import com.rabbit.samples.nicknameservice.persistence.entities.Nickname;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * NOT RELATED TO NICKNAME-SERVICE FUNCTIONALITIES, but relevant for spring-testing.
 * This is just a test to verify different scenarios.
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@Service("fakeService")
@Transactional
public class FakeServiceImpl {

	@Autowired
	@Qualifier("restTemplate")
	RestTemplate restTemplate;

	public String getFakeString() {

		log.debug("get fake string...");

		final int port = 8082;
		final String url = "http://localhost:" + port;

		return getRestTemplate().getForObject(url, String.class);
	}

	public String postFakeString(final String comment) {

		log.debug("post fake string {}...", comment);

		try {
			// PLEASE NOTE: the port is not specified on purpose!!
			// final String url = "http://localhost/add-comment";

			final int port = 8082;
			final String url = "http://localhost:" + port + "/add-comment";

			return getRestTemplate().postForObject(url, comment, String.class);

		} catch (final RestClientException e) {
			final String errMsg = e.getMessage();
			log.error("Exception occurred posting fake string {}: {}", comment, errMsg);
			return errMsg;
		}
	}


	public Nickname getFakeNickname(final String employeeName) {

		log.debug("get fake nickname by employeeName {} with REST-TEMPLATE...", employeeName);

		final int port = 8082;
		final String url = "http://localhost:" + port + "/fakenick";

		final EmployeeDto employeeDto = getRestTemplate().getForObject(
				UriComponentsBuilder
						.fromUriString(url)
						.buildAndExpand()
						.toUri(),
				EmployeeDto.class);

		if (employeeDto == null) {
			return createEmptyNickname();
		}

		return Nickname.builder().nickname("Uncle Bob").build();
	}

	protected Nickname createEmptyNickname() {

		return Nickname.builder().build();
	}

}
