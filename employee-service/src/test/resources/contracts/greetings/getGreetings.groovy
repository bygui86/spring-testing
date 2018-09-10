package contracts.greetings

import org.springframework.cloud.contract.spec.Contract

Contract.make {

	// If you want to ignore a contract
	// ignored

	// The name of the test method can be inferred either by the name of the file or by the name attribute like here below
	// name "greetings"

	description "should return greetings as string"

	request {
		method GET()
		url("/")
	}

	response {
		status 200
		body("Greetings from employee-service ;)")
	}

}
