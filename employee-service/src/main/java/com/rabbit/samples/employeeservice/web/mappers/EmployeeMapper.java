package com.rabbit.samples.employeeservice.web.mappers;

import com.rabbit.samples.employeeservice.persistence.entities.Employee;
import com.rabbit.samples.employeeservice.web.dtos.EmployeeDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;


@Mapper(implementationPackage = "<PACKAGE_NAME>.impl")
public interface EmployeeMapper {

	@Mappings({
			// no specific mappings
	})
	EmployeeDto mapEntityToDto(final Employee model);

	@InheritInverseConfiguration
	Employee mapDtoToEntity(final EmployeeDto dto);

}
