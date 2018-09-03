package com.rabbit.samples.nicknameservice.configs;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@Profile("resttemplate")
@Configuration("restTemplateConfig")
public class RestTemplateConfig {

	@Value("${rest.client.request.connect.timeout:2000}")
	int connectTimeout;

	@Value("${rest.client.request.connection.request.timeout:5000}")
	int connectionRequestTimeout;

	@Value("${rest.client.request.read.timeout:10000}")
	int readTimeout;

	@Bean("restTemplate")
	public RestTemplate createRestTemplate() {

		log.debug("Creating RestTemplate...");

		return new RestTemplateBuilder()
				.requestFactory(this::getClientHttpRequestFactory)
				.build();
	}

	private ClientHttpRequestFactory getClientHttpRequestFactory() {

		final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setConnectTimeout(getConnectTimeout());
		requestFactory.setConnectionRequestTimeout(getConnectionRequestTimeout());
		requestFactory.setReadTimeout(getReadTimeout());
		return requestFactory;
	}

}
