package com.rabbit.samples.groupservice.web.controllers;

import com.rabbit.samples.groupservice.feign.clients.EmployeeServiceClient;
import com.rabbit.samples.groupservice.feign.dtos.InfoDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@RestController("infoRestController")
@RequestMapping("/info")
class InfoRestController {

	int idCounter = 0;

	@Value("${spring.application.name}")
	String appName;

	@Value("${producer.ribbon.listOfServers}")
	List<String> ribbonListOfServers;

	@Autowired
	private EmployeeServiceClient employeeServiceClient;

	@GetMapping
	public InfoDto info() {

		log.info("Getting info...");

		idCounter++;
		return InfoDto.builder()
				.id(getIdCounter())
				.name(getAppName())
				.dateTime(LocalDateTime.now())
				.build();
	}

	@GetMapping("/all")
	public List<InfoDto> allInfo() {

		log.info("Getting info from current service {} and from another service {}",
				getAppName(), getRibbonListOfServers().get(0));

		final List<InfoDto> infos = new ArrayList<>();
		// add current info
		infos.add(
				info()
		);
		// add producer-service info
		infos.add(
				getEmployeeServiceClient().getInfo()
		);

		return infos;
	}

}
