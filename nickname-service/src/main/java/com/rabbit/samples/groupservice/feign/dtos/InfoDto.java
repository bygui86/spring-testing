package com.rabbit.samples.groupservice.feign.dtos;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class InfoDto {

	@NonNull
	int id;

	@NonNull
	String name;

	@NonNull
	LocalDateTime dateTime;

}
