package com.rabbit.samples.groupservice.configs;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;


@Configuration("feignConfig")
@EnableFeignClients(basePackages = "com.rabbit.samples.groupservice.feign.clients")
public class FeignConfig {

	// no-op
}
