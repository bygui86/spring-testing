package com.rabbit.samples.employeeservice.web.controllers;

import com.rabbit.samples.employeeservice.web.dtos.InfoDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@RestController("infoRestController")
@RequestMapping("/info")
public class InfoRestController {

	int idCounter = 0;

	@Value("${spring.application.name}")
	String appName;

	@GetMapping
	public InfoDto getInfo() {

		idCounter++;
		return InfoDto.builder()
				.id(getIdCounter())
				.name(getAppName())
				.dateTime(LocalDateTime.now())
				.build();
	}

}
