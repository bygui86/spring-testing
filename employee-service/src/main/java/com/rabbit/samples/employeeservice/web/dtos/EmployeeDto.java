package com.rabbit.samples.employeeservice.web.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class EmployeeDto {

	Long id;

	@NotEmpty
	@Size(min = 3, max = 20)
	String name;

}
