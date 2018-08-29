package com.rabbit.samples.groupservice.feign.clients;

import com.rabbit.samples.groupservice.feign.dtos.InfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient("employee")
public interface EmployeeServiceClient {

	@RequestMapping(method = RequestMethod.GET, value = "/")
	String greet();

	@RequestMapping(method = RequestMethod.GET, value = "/info")
	InfoDto getInfo();

	// TODO add other endpoints

}
