package com.rabbit.samples.employeeservice.unittests.web.controllers;

import com.rabbit.samples.employeeservice.persistence.entities.Employee;
import com.rabbit.samples.employeeservice.services.EmployeeService;
import com.rabbit.samples.employeeservice.utils.JsonUtil;
import com.rabbit.samples.employeeservice.web.controllers.EmployeeRestController;
import com.rabbit.samples.employeeservice.web.dtos.EmployeeDto;
import com.rabbit.samples.employeeservice.web.mappers.EmployeeMapper;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;


@IfProfileValue(name = "spring.testing.profile", values = {"all", "unit"})
@RunWith(SpringRunner.class)
/*
	To test a Controller, we can use @WebMvcTest: it will auto-configure the Spring MVC infrastructure for our unit test.

	PLEASE NOTE:
	In most of the cases, @WebMvcTest will be limited to bootstrap a single controller.
 */
@WebMvcTest(EmployeeRestController.class)
public class EmployeeRestControllerUnitTest {

	/*
		@WebMvcTest also auto-configures MockMvc which offers a powerful way of easy testing MVC controllers without starting a full HTTP server.
	 */
	@Autowired
	private MockMvc mockMvc;

	@MockBean(name = "employeeService")
	private EmployeeService employeeService;

	@MockBean
	private EmployeeMapper employeeMapper;

	final String name = "alex";

	public void before() {

	}

	@Test
	public void whenGetAll_thenReturnGivenEmployees()
			throws Exception {

		// given
		final Employee alex = Employee.builder().name(name).build();
		final List<Employee> allEmployees = Collections.singletonList(alex);
		final EmployeeDto alexDto = EmployeeDto.builder().name(name).build();

		BDDMockito
				.given(employeeService.getAll())
				.willReturn(allEmployees);

		BDDMockito
				.given(employeeMapper.mapEntityToDto(alex))
				.willReturn(alexDto);

		mockMvc
				// when
				.perform(
						MockMvcRequestBuilders
								.get("/employees")
								.accept(MediaType.APPLICATION_JSON)
				)

				// then
				.andExpect(
						MockMvcResultMatchers.status().isOk()
				)
				.andExpect(
						MockMvcResultMatchers
								.content()
								.contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1))
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$[0]", Matchers.notNullValue())
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is(name))
				)
		;

		// verify
		BDDMockito.verify(employeeService, VerificationModeFactory.times(1)).getAll();
		BDDMockito.reset(employeeService);
	}

	@Test
	public void whenGetByName_thenReturnGivenEmployee() throws Exception {

		// given
		final Employee alex = Employee.builder().name(name).build();
		final EmployeeDto alexDto = EmployeeDto.builder().name(name).build();

		BDDMockito
				.given(employeeService.getByName(name))
				.willReturn(alex);

		BDDMockito
				.given(employeeMapper.mapEntityToDto(alex))
				.willReturn(alexDto);

		mockMvc
				// when
				.perform(
						MockMvcRequestBuilders
								.get("/employees/" + name)
								.accept(MediaType.APPLICATION_JSON)
				)

				// then
				.andExpect(
						MockMvcResultMatchers.status().isOk()
				)
				.andExpect(
						MockMvcResultMatchers
								.content()
								.contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue())
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(name))
				)
		;

		// verify
		BDDMockito.verify(employeeService, VerificationModeFactory.times(1)).getByName(BDDMockito.any());
		BDDMockito.reset(employeeService);
	}

	@Test
	public void whenPost_thenCreateAndReturnEmployee() throws Exception {

		// given
		final Employee alex = Employee.builder().name(name).build();
		final EmployeeDto alexDto = EmployeeDto.builder().name(name).build();

		BDDMockito
				.given(employeeService.save(BDDMockito.any()))
				.willReturn(alex);

		BDDMockito
				.given(employeeMapper.mapDtoToEntity(alexDto))
				.willReturn(alex);

		BDDMockito
				.given(employeeMapper.mapEntityToDto(alex))
				.willReturn(alexDto);

		mockMvc
				// when
				.perform(
						MockMvcRequestBuilders
								.post("/employees")
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.content(JsonUtil.toJson(alexDto))
				)

				// then
				.andExpect(
						MockMvcResultMatchers.status().isCreated()
				)
				.andExpect(
						MockMvcResultMatchers
								.content()
								.contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue())
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.name", Matchers.is(name))
				)
		;

		// verify
		BDDMockito.verify(employeeService, VerificationModeFactory.times(1)).save(BDDMockito.any());
		BDDMockito.reset(employeeService);
	}

}
