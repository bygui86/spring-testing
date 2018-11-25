# Spring Testing sample project

## Desciption
In this sample project we are exploring the different testing techniques using some test frameworks.

###### PLEASE NOTE: The purpose of this sample project is not to achieve a complete test coverage!

---

## Tech stack

#### Application
* Java 11 (no modules)
* Maven Wrapper 3.x
* Spring Boot 2.1.x

#### Testing
* JUnit 4
* Mockito
* Assertj Assertions
* Spring Boot test
* Spring Cloud Contract

---

## Build & run

#### Pre-requisites
No specific pre-requisites

#### Make (in every sub-project)
* just compile

		make clean compile

* build and run unit tests

		make clean build

* install

		make install

* run

		make run

* debug

		make debug

* run integration tests

		make integration-test

* run all tests

		make test

* docker
	* build image
	
			make docker-build
	
	* run container
	
			make docker-run
	
	* run container as daemon
	
			make docker-run-daemon
	
	* stop daemon container
	
			make docker-stop

#### Maven & Docker (in every sub-project)
* just compile

		./mvnw clean compile

* build and run unit tests

		./mvnw -Dtest=*UnitTest clean package

* install

		./mvnw clean install

* run

		./mvnw spring-boot:run -DskipTests -Dspring-boot.run.jvmArguments='$(MEM_OPTS) $(JMX_OPTS) $(OTHER_OPTS)'

* debug

		./mvnw spring-boot:run -DskipTests -Dspring-boot.run.jvmArguments='$(MEM_OPTS) $(JMX_OPTS) $(OTHER_OPTS) -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=$(IMAGE_DEBUG_PORT)'

* run integration tests

		./mvnw -Dtest=*IntegrationTest -DfailIfNoTests=false test

* run all tests

		./mvnw test

* docker
	* build image

			./mvnw clean package
			docker build -f Dockerfile_local -t $(IMAGE_NAME):$(IMAGE_TAG) .
	
	* run container
	
			docker run --rm -it --name $(NAME) $(DOCKER_IMAGE_PORTS) --net bridge --add-host=$(DOCKER_HOST):$(DOCKER_IP) $(IMAGE_NAME):$(IMAGE_TAG)
	
	* run container as daemon
	
			docker run --rm -d --name $(NAME) $(DOCKER_IMAGE_PORTS) --net bridge --add-host=$(DOCKER_HOST):$(DOCKER_IP) $(IMAGE_NAME):$(IMAGE_TAG)
	
	* stop daemon container
	
			docker container stop -f $(NAME)

---

## TODOs
* fix issue on contract testing
* random testing (?)
* test containers

---

## Related repos

* [spring-testing-contracts-stubs](https://github.com/bygui86/spring-testing-contracts-stubs)

---

## Links

###### DONE
* theory
	* https://martinfowler.com/bliki/UnitTest.html
	* https://martinfowler.com/bliki/TestDouble.html
	* https://www.martinfowler.com/articles/mocksArentStubs.html
* tests separation
	* https://www.testwithspring.com/lesson/running-integration-tests-with-maven/
* unit/integration testing
	* https://www.baeldung.com/spring-boot-testing
	* https://www.baeldung.com/injecting-mocks-in-spring
	* https://www.baeldung.com/spring-boot-testresttemplate
	* spring @restclienttest
		* https://www.baeldung.com/restclienttest-in-spring-boot
		* https://objectpartners.com/2013/01/09/rest-client-testing-with-mockrestserviceserver/
		* https://www.dontpanicblog.co.uk/2016/07/16/resttemplatebuilder-and-restclienttest-in-spring-boot-1-4-0/
		* https://examples.javacodegeeks.com/enterprise-java/spring/using-mockrestserviceserver-test-rest-client/
	* feign client
		* https://stackoverflow.com/questions/34397570/mock-an-eureka-feign-client-for-unittesting
		* https://www.blazemeter.com/blog/Rest-API-testing-with-Spring-Cloud-Feign-Clients
	* https://www.baeldung.com/integration-testing-in-spring
	* https://www.baeldung.com/spring-webappconfiguration
	* https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html
	* web
		* https://docs.spring.io/spring-security/site/docs/current/reference/html/test-mockmvc.html
	* configurations
		* https://www.baeldung.com/spring-webappconfiguration
	    * https://stackoverflow.com/questions/42912616/spring-test-context-best-practice
* contract testing
	* https://martinfowler.com/articles/consumerDrivenContracts.html
	* https://www.baeldung.com/spring-cloud-contract
	* https://cloud.spring.io/spring-cloud-contract/
	* https://spring.io/guides/gs/contract-rest
	* https://cloud.spring.io/spring-cloud-contract/single/spring-cloud-contract.html
	* multiple base classes
		* https://cloud.spring.io/spring-cloud-contract/single/spring-cloud-contract.html#maven-different-base
	* dsl
		* https://cloud.spring.io/spring-cloud-contract/single/spring-cloud-contract.html#contract-dsl
	* multiple contracts in one file
		* https://cloud.spring.io/spring-cloud-contract/single/spring-cloud-contract.html#_multiple_contracts_in_one_file
	* pushing to repo
		* https://cloud.spring.io/spring-cloud-contract/single/spring-cloud-contract.html#maven-pushing-stubs-to-scm
	* pulling from repo
		* https://cloud.spring.io/spring-cloud-contract/single/spring-cloud-contract.html#scm-stub-downloader
* maven properties
	* https://books.sonatype.com/mvnref-book/reference/resource-filtering-sect-properties.html

###### IN-PROG
/

###### TODO
* theory
	* https://martinfowler.com/bliki/IntegrationTest.html
	* https://martinfowler.com/articles/practical-test-pyramid.html
* unit/integration testing
	* https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html
	* https://dzone.com/articles/how-to-mock-spring-bean-version-2
	* feign client
		* https://github.com/velo/feign-mock
* contract testing
	* multiple consumers
		* https://cloud.spring.io/spring-cloud-contract/single/spring-cloud-contract.html#_stubs_per_consumer
	* stub runner for messaging
		* https://cloud.spring.io/spring-cloud-contract/single/spring-cloud-contract.html#stub-runner-for-messaging
* security testing
	* https://www.baeldung.com/spring-security-integration-tests
	* https://docs.spring.io/spring-security/site/docs/current/reference/html/test-method.html
* wiremock
	* http://wiremock.org/docs/stubbing/
	* https://www.youtube.com/watch?v=x3MvZ8DFrpE&feature=youtu.be
* cloud-native
	* https://www.infoq.com/articles/test-java-microservices-service-mesh
* mock-server
	* http://www.mock-server.com/
	* https://github.com/jamesdbloom/mockserver
* spock
	* ?
* junit5
	* https://www.baeldung.com/junit-5
	* https://www.infoq.com/articles/deep-dive-junit5-extensions?utm_source=infoqEmail&utm_medium=SpecialNL_EditorialContent&utm_campaign=08272018_SpecialNL&forceSponsorshipId=1656
	* http://minborgsjavapot.blogspot.com/2018/10/blow-up-your-junit-tests-with.html?m=1

#### Issues
* https://github.com/spring-projects/spring-boot/issues/6541
* https://moelholm.com/2016/10/22/spring-boot-separating-tests/
* https://stackoverflow.com/questions/47979589/spring-data-jpa-generate-id-on-database-side-only-before-create
* https://stackoverflow.com/questions/35232827/spring-boot-test-ignores-logging-level
* https://github.com/spring-cloud/spring-cloud-netflix/issues/1482
* https://www.concretepage.com/questions/535
* https://www.mkyong.com/java/jackson-2-convert-java-object-to-from-json/
* https://stackoverflow.com/questions/43093968/enablefeignclients-and-feignclient-fail-on-autowiring-feigncontext-nosuchbea

#### Repos
* https://github.com/spring-cloud-samples/spring-cloud-contract-samples/
	* https://github.com/spring-cloud-samples/spring-cloud-contract-samples/tree/master/consumer
	* https://github.com/spring-cloud-samples/spring-cloud-contract-samples/tree/master/producer
* https://github.com/hamvocke/spring-testing
* https://github.com/importsource/spring-cloud-contract
* https://github.com/paolocarta/spring-cloud-contract-demo
