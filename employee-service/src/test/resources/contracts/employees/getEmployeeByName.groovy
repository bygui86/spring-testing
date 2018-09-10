package contracts.employees

import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

Contract.make {

	description "should return an employee by given name"

	request {
		method GET()
		url("/employees/name") {
			queryParameters {
				parameter "name", "Robert Martin"
			}
		}
	}

	response {
		status 200
		headers {
			header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
		}
		body([id: 1L, name: "Robert Martin"])
	}

}
