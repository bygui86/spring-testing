package com.rabbit.samples.nicknameservice.services.impl;

import com.rabbit.samples.nicknameservice.feign.dtos.EmployeeDto;
import com.rabbit.samples.nicknameservice.persistence.entities.Nickname;
import com.rabbit.samples.nicknameservice.persistence.repos.NicknameRepository;
import com.rabbit.samples.nicknameservice.services.NicknameService;
import com.rabbit.samples.nicknameservice.utils.UrlUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@Profile("resttemplate")
@Service("nicknameService")
@Transactional
public class RestTemplateNicknameServiceImpl implements NicknameService {

	static final String EMPLOYEE_NAME_ENDPOINT = "/employees/name";

	static final String EMPLOYEE_NAME_PARAM = "name";

	@Value("${rest.client.protocol:http}")
	String protocol;

	@Value("${employee.service.root.url}")
	String employeeRootUrl;

	@Value("${rest.client.url.encoding:UTF-8}")
	String charEncoding;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	@Qualifier("nicknameRepository")
	NicknameRepository nicknameRepository;

	@Override
	public List<Nickname> getAll() {

		log.debug("get all nicknames...");

		return getNicknameRepository().findAll();
	}

	@Override
	public Nickname getByEmployeeName(final String employeeName) {

		log.debug("get nickname by employeeName {} with REST-TEMPLATE...", employeeName);

		final EmployeeDto employeeDto = getRestTemplate().getForObject(
				UriComponentsBuilder
						.fromUriString(getEmployeeNameUrl())
						.queryParam(EMPLOYEE_NAME_PARAM, UrlUtils.urlEncodeString(employeeName, getCharEncoding()))
						.buildAndExpand()
						.toUri(),
				EmployeeDto.class);

		if (employeeDto == null) {
			return createEmptyNickname();
		}

		final Nickname nickname = getNicknameRepository().findByEmployeeId(employeeDto.getId());
		if (nickname == null) {
			return createEmptyNickname();
		}
		return nickname;
	}

	protected String getEmployeeNameUrl() {

		return new StringBuilder()
				.append(getProtocol())
				.append(UrlUtils.URL_PROTOCOL_SEPARATOR)
				.append(getEmployeeRootUrl())
				.append(EMPLOYEE_NAME_ENDPOINT)
				.toString();
	}

	protected Nickname createEmptyNickname() {

		return Nickname.builder().build();
	}

}
