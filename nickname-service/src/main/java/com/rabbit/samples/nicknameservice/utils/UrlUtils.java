package com.rabbit.samples.nicknameservice.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Slf4j
public final class UrlUtils {

	private UrlUtils() {

		// no-op
	}

	public final static String URL_PROTOCOL_SEPARATOR = "://";

	public static String urlEncodeString(final String str, final String charEncoding) {

		try {
			return URLEncoder.encode(str, charEncoding);

		} catch (final UnsupportedEncodingException e) {
			log.error("An Exception occurred: " + e.getMessage(), e);
			log.warn("Returning plain string: {}", str);
		}
		return str;
	}

}
