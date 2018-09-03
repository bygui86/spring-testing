package com.rabbit.samples.nicknameservice.configs;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Profile("feign")
@Configuration("feignConfig")
@EnableFeignClients(basePackages = "com.rabbit.samples.nicknameservice.feign.clients")
public class FeignConfig {

	// no-op
}
