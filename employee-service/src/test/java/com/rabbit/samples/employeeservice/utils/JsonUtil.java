package com.rabbit.samples.employeeservice.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


public final class JsonUtil {

	private JsonUtil() {
		// no-op
	}

	public static byte[] toJson(final Object object) throws IOException {

		final ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

}
