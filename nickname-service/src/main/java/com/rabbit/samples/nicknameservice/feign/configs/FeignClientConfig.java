package com.rabbit.samples.nicknameservice.feign.configs;

import feign.codec.Encoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


/*
	This configurations could be used in the @FeignClient on top of a service client
 */
public class FeignClientConfig {

	public Encoder feignEncoder() {

		return new SpringEncoder(
				() -> new HttpMessageConverters(
						new MappingJackson2HttpMessageConverter()
				)
		);
	}

}
