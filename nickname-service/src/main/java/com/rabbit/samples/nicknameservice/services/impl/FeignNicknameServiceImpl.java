package com.rabbit.samples.nicknameservice.services.impl;

import com.rabbit.samples.nicknameservice.feign.clients.EmployeeServiceClient;
import com.rabbit.samples.nicknameservice.feign.dtos.EmployeeDto;
import com.rabbit.samples.nicknameservice.persistence.entities.Nickname;
import com.rabbit.samples.nicknameservice.persistence.repos.NicknameRepository;
import com.rabbit.samples.nicknameservice.services.NicknameService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@Profile("feign")
@Service("nicknameService")
@Transactional
public class FeignNicknameServiceImpl implements NicknameService {

	@Autowired
	EmployeeServiceClient employeeServiceClient;

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

		log.debug("get nickname by employeeName {} with FEIGN...", employeeName);

		final EmployeeDto employeeDto = getEmployeeServiceClient().getEmployeeByName(employeeName);
		if (employeeDto == null) {
			return createEmptyNickname();
		}

		final Nickname nickname = getNicknameRepository().findByEmployeeId(employeeDto.getId());
		if (nickname == null) {
			return createEmptyNickname();
		}
		return nickname;
	}

	protected Nickname createEmptyNickname() {

		return Nickname.builder().build();
	}

}
