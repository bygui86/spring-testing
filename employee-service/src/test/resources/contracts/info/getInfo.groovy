package contracts.employees

import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

import java.time.LocalDateTime

Contract.make {

	description "should return general info about employee-service"

	request {
		method GET()
		url("/info")
	}

	response {
		status 200
		headers {
			header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
		}
		body([id: 1, name: "employee-service", dateTime: LocalDateTime.now().toString()])
	}

}
