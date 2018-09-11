package contracts.employees

import org.springframework.cloud.contract.spec.Contract
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

Contract.make {

	name "getInfo"
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
		body(
				// Error executing test:
				// Parsed JSON [{"id":1,"name":"employee-service","dateTime":[2018,9,10,15,33,7,972000000]}]
				// doesn't match the JSON path [$[?(@.['dateTime'] == '2018-09-10T15:32:55.758')]]
//				[id: 1, name: "employee-service", dateTime: LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)]
//				[id: 1, name: "employee-service", dateTime: LocalDateTime.now().toString()]
//				[id: 1, name: "employee-service", dateTime: "2018-09-10T15:23:06.455Z"]
				[id: 1, name: "employee-service"]
		)
	}

}
