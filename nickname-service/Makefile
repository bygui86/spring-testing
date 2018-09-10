
# Makefile
# see https://www.gnu.org/software/make/manual/make.html


# VARIABLES

NAME := nickname-service

BUILD_TOOL := ./mvnw
JAR_FILE := $(shell find target -name '*.jar' 2>/dev/null)

IMAGE_NAME := samples/$(NAME)
IMAGE_TAG := 0.0.1
IMAGE_EXPOSE_PORT_1 := 8080
IMAGE_EXPOSE_PORT_2 := 8090
DOCKER_HOST_IP := localhost
DOCKER_HOST_PORT := 5000

# see https://docs.spring.io/spring-boot/docs/2.0.3.RELEASE/maven-plugin/run-mojo.html#jvmArguments
MEM_OPTS := -Xms128m -Xmx128m
#JMX_OPTS := -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=3$(IMAGE_EXPOSE_PORT_2) -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
DEBUG_OPTS := -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005
OTHER_OPTS := -Dspring.profiles.active=local,resttemplate

.PHONY: help
.DEFAULT_GOAL := help


# GENERAL

help :		## Help
	@echo ""
	@echo "*** $(NAME) Makefile help ***"
	@echo ""
	@echo "Targets list:"
	@grep -E '^[a-zA-Z_-]+ :.*?## .*$$' $(MAKEFILE_LIST) | sort -k 1,1 | awk 'BEGIN {FS = ":.*?## "}; {printf "\t\033[36m%-30s\033[0m %s\n", $$1, $$2}'
	@echo ""

print-variables :		## Print variables values
	@echo "MAKE: $(MAKE)"
	@echo "MAKEFILES: $(MAKEFILES)"
	@echo "MAKEFILE_LIST: $(MAKEFILE_LIST)"
	@echo "- - - "
	@echo "NAME: $(NAME)"
	@echo "- - - "
	@echo "BUILD_TOOL: $(BUILD_TOOL)"
	@echo "GIT_COMMIT_HASH: $(GIT_COMMIT_HASH)"
	@echo "JAR_FILE: $(JAR_FILE)"
	@echo "- - - "
	@echo "IMAGE_NAME: $(IMAGE_NAME)"
	@echo "IMAGE_TAG: $(IMAGE_TAG)"
	@echo "IMAGE_EXPOSE_PORT_1: $(IMAGE_EXPOSE_PORT_1)"
	@echo "IMAGE_EXPOSE_PORT_2: $(IMAGE_EXPOSE_PORT_2)"
	@echo "DOCKER_HOST_IP: $(DOCKER_HOST_IP)"
	@echo "DOCKER_HOST_PORT: $(DOCKER_HOST_PORT)"
	@echo "- - - "
	@echo "MEM_OPTS: $(MEM_OPTS)"
	@echo "JMX_OPTS: $(JMX_OPTS)"
	@echo "DEBUG_OPTS: $(DEBUG_OPTS)"
	@echo "OTHER_OPTS: $(OTHER_OPTS)"


# BUILDING

build :		## Build the application including UnitTests
	$(BUILD_TOOL) -Dtest=*UnitTest -DfailIfNoTests=false package

build-no-tests :		## Build the application excluding tests
	$(BUILD_TOOL) package -DskipTests

clean :		## Clean the application
	$(BUILD_TOOL) clean

clean-build :		## Clean and Build the application excluding tests
	$(BUILD_TOOL) -Dtest=*UnitTest -DfailIfNoTests=false clean package

clean-build-no-tests :		## Clean and Build the application excluding tests
	$(BUILD_TOOL) clean package -DskipTests

install : clean-build		## Install the library into maven repo
	$(BUILD_TOOL) install

dependencies :		## Show dependency tree
	$(BUILD_TOOL) dependency:tree -Dverbose


# TESTING

# CLASS NAME selective run (-Dtest=*UnitTest)

test :		## Run ALL tests in the application
	$(BUILD_TOOL) -DfailIfNoTests=false -Dtest=*Test test

unit-test :		## Run ONLY Unit tests in the application
	$(BUILD_TOOL) -DfailIfNoTests=false -Dtest=*UnitTest test

integration-test :		## Run ONLY Integration tests in the application
	$(BUILD_TOOL) -DfailIfNoTests=false -Dtest=*IntegrationTest test

contract-test :		## Run ONLY Contract tests in the application
	$(BUILD_TOOL) -DfailIfNoTests=false -Dtest=*ContractTest test

# IF-PROFILE-VALUE ANNOTATION selective run (-Dspring.testing.profile=unit)

#test :		## Run ALL tests in the application
#	$(BUILD_TOOL) -DfailIfNoTests=false -Dspring.testing.profile=all test
#
#unit-test :		## Run ONLY Unit tests in the application
#	$(BUILD_TOOL) -DfailIfNoTests=false -Dspring.testing.profile=unit test
#
#integration-test :		## Run ONLY Integration tests in the application
#	$(BUILD_TOOL) -DfailIfNoTests=false -Dspring.testing.profile=integration  test
#
#contract-test :		## Run ONLY Contract tests in the application
#	$(BUILD_TOOL) -DfailIfNoTests=false -Dspring.testing.profile=contract test

# JUNIT-CATEGORY selective run (-Dspring.testing.profile=unit)
# see
#	. https://moelholm.com/2016/10/22/spring-boot-separating-tests/
#	. https://www.testwithspring.com/lesson/running-integration-tests-with-maven/


# RUNNING

run :		## Run the application through Spring Boot plugin
	$(BUILD_TOOL) spring-boot:run -DskipTests -Dspring-boot.run.jvmArguments='$(MEM_OPTS) $(JMX_OPTS) $(OTHER_OPTS)'

debug :		## Run the application in debug mode through Spring Boot plugin
	$(BUILD_TOOL) spring-boot:run -DskipTests -Dspring-boot.run.jvmArguments='$(MEM_OPTS) $(JMX_OPTS) $(DEBUG_OPTS) $(OTHER_OPTS)'

java-run :		## Run the application through the generated fat-jar
	java $(MEM_OPTS) $(JMX_OPTS) $(OTHER_OPTS) -jar $(JAR_FILE)

java-debug :		## Run the application in debug mode through the generated fat-jar
	java $(MEM_OPTS) $(JMX_OPTS) $(DEBUG_OPTS) $(OTHER_OPTS) -jar $(JAR_FILE)


# DOCKER

docker-prepare :		## Prepare application to be containerised
	$(BUILD_TOOL) -DfailIfNoTests=false clean package

docker-build : docker-prepare		## Build docker image
	docker build -t $(IMAGE_NAME):$(IMAGE_TAG) .

docker-run :		## Run docker image as deamon
	docker run -d --rm --name $(NAME) -p $(IMAGE_EXPOSE_PORT_1):$(IMAGE_EXPOSE_PORT_1) $(IMAGE_EXPOSE_PORT_2):$(IMAGE_EXPOSE_PORT_2) $(IMAGE_NAME):$(IMAGE_TAG)

docker-run-temp :		## Run docker image as temporary foreground
	docker run -ti --rm --name $(NAME) -p $(IMAGE_EXPOSE_PORT_1):$(IMAGE_EXPOSE_PORT_1) $(IMAGE_EXPOSE_PORT_2):$(IMAGE_EXPOSE_PORT_2) $(IMAGE_NAME):$(IMAGE_TAG)

docker-stop :		## Stop docker image
	docker container stop -f $(NAME)

docker-kill :		## Kill docker image
	docker container kill $(NAME)

docker-push : docker-build		## Push docker image to docker registry
	docker push $(DOCKER_HOST_IP):$(DOCKER_HOST_PORT)/$(IMAGE_NAME):$(IMAGE_TAG)

docker-delete-local : docker-stop		## Delete docker image from local
	docker container rm -f $(NAME)
	docker image rm -f $(NAME)

docker-delete-remote : docker-stop		## Delete the docker image from remote
	docker image remove $(DOCKER_HOST_IP):$(DOCKER_HOST_PORT)/$(IMAGE_NAME):$(IMAGE_TAG)
