package com.rabbit.samples.employeeservice.unittests.web.mappers;


import com.rabbit.samples.employeeservice.persistence.entities.Employee;
import com.rabbit.samples.employeeservice.web.dtos.EmployeeDto;
import com.rabbit.samples.employeeservice.web.mappers.EmployeeMapper;
import com.rabbit.samples.employeeservice.web.mappers.impl.EmployeeMapperImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;


@IfProfileValue(name = "spring.testing.profile", values = {"all", "unit"})
@RunWith(SpringRunner.class)
public class EmployeeMapperUnitTest {

	@TestConfiguration
	@Import(EmployeeMapperImpl.class)
	static class EmployeeServiceUnitTestContextConfiguration {

		// no-op
	}


	@Autowired
	EmployeeMapper employeeMapper;


	final Long id = 1L;

	final String name = "alex";

	@Test
	public void givenEntity_thenConvertToDto() throws Exception {

		// given
		final Employee entity = Employee.builder().id(id).name(name).build();

		// when
		final EmployeeDto dto = employeeMapper.mapEntityToDto(entity);

		// then
		Assert.assertEquals(id, dto.getId());
		Assert.assertEquals(name, dto.getName());
	}

	@Test
	public void givenDto_thenConvertToEntity() throws Exception {

		// given
		final EmployeeDto dto = EmployeeDto.builder().id(id).name(name).build();

		// when
		final Employee entity = employeeMapper.mapDtoToEntity(dto);

		// then
		Assert.assertEquals(id, entity.getId());
		Assert.assertEquals(name, entity.getName());
	}

}
