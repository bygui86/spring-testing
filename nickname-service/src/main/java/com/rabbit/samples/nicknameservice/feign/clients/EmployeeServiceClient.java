package com.rabbit.samples.nicknameservice.feign.clients;

import com.rabbit.samples.nicknameservice.feign.dtos.EmployeeDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

// import com.rabbit.samples.nicknameservice.feign.configs.FeignClientConfig;


@FeignClient(
		name = "employee",
		qualifier = "employeeServiceClient",
		url = "http://${employee.service.host}:${employee.service.port}"
		// configuration = FeignClientConfig.class
)
public interface EmployeeServiceClient {

	/*
		!! IMPORTANT !!
		PLEASE NOTE:
		OpenFeign DOES NOT infer the parameters name from the parameter itself, so we must specify it in the @RequestParam annotation.
	 */
	@RequestMapping(
			method = RequestMethod.GET,
			value = "/employees/name"
	)
	@Headers("Accept: application/json")
	// TODO: seems not working properly, try @HeaderMap
	EmployeeDto getEmployeeByName(
			@RequestParam("name") final String employeeName);

}
