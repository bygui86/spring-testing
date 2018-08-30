package com.rabbit.samples.nicknameservice.feign.clients;

import com.rabbit.samples.nicknameservice.feign.dtos.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("employee")
public interface EmployeeServiceClient {

	/*
		!! IMPORTANT !!
		PLEASE NOTE:
		OpenFeign DOES NOT infer the parameters name from the parameter itself, so we must specify it in the @RequestParam annotation.
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/employees/name")
	EmployeeDto getByEmployeeByName(
			@RequestParam("name") final String employeeName);

}
