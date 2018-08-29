package com.rabbit.samples.employeeservice.web.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InfoDto {

	int id;

	String name;

	LocalDateTime dateTime;

}
