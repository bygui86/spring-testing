package com.rabbit.samples.groupservice.web.controllers;

import com.rabbit.samples.groupservice.feign.clients.EmployeeServiceClient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@RestController("greetingsRestController")
@RequestMapping("/")
class GreetingsRestController {

	@Value("${spring.application.name}")
	String appName;

	@Value("${producer.ribbon.listOfServers}")
	List<String> ribbonListOfServers;

	@Autowired
	private EmployeeServiceClient employeeServiceClient;

	@GetMapping
	public String greet() {

		log.info("Getting greetings...");

		return "Greetings from " + getAppName() + " ;)";
	}

	@GetMapping("/all")
	public List<String> allGreet() {

		log.info("Getting greetings from current service {} and from another service {}",
				getAppName(), getRibbonListOfServers().get(0));

		final List<String> greetings = new ArrayList<>();
		// add current greetings
		greetings.add(
				greet()
		);
		// add others greetings
		greetings.add(
				getEmployeeServiceClient().greet()
		);

		return greetings;
	}

}
