package contracts.employees

import org.springframework.cloud.contract.spec.Contract

[
		Contract.make {

			name "getAllEmployees"
			description "should return a list of employees"

			request {
				method GET()
				headers {
					accept applicationJson()
				}
				url("/employees")
			}

			response {
				status 200
				headers {
					contentType applicationJson()
					// more verbose alternative
					// header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
				}
				body(
//						TODO to be tested
//						responses: [
						[
								[id: 1L, name: "Robert Martin"],
								[id: 2L, name: "Martin Fowler"],
								[id: 3L, name: "Sam Newman"],
								[id: 4L, name: "Kent Beck"]
						]
				)
			}

		},

		Contract.make {

			name "getEmployeeById"
			description "should return an employee by given id"

			request {
				method GET()
				headers {
					accept applicationJson()
				}
				url("/employees/id") {
					queryParameters {
						parameter "id", 2
					}
				}
			}

			response {
				status 200
				headers {
					contentType applicationJson()
				}
				body(
						// Taking the response body from a json file
						file("getEmployeeByIdResponse.json")
				)
			}

		},

		Contract.make {

			name "getEmployeeByName"
			description "should return an employee by given name"

			request {
				method GET()
				headers {
					accept applicationJson()
				}
				url("/employees/name") {
					queryParameters {
						parameter "name", "Robert Martin"
					}
				}
			}

			response {
				status 200
				headers {
					contentType applicationJson()
				}
				body(
						[id: 1L, name: "Robert Martin"]
				)
			}

		},

		Contract.make {

			name "createEmployee"
			description "should return the employee just created"

			request {
				method POST()
				headers {
					accept applicationJson()
					contentType applicationJson()
				}
				url("/employees") {
					body(
							[name: "James Gosling"]
					)
				}
			}

			response {
				status 201
				headers {
					contentType applicationJson()
				}
				body(
//						[id: 5L, name: "James Gosling"]
						// $(anyNumber()): will give back a random generated number
						// fromRequest().body('$.name'): will take the name from the request body json
						[id: $(anyNumber()), name: fromRequest().body('$.name')]
				)
			}

		},

]
