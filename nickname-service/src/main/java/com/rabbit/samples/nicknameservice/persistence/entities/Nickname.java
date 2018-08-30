package com.rabbit.samples.nicknameservice.persistence.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Entity
@Table(name = "nicknames")
public class Nickname {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	// @NotNull
	Long employeeId;

	@NotEmpty
	@Size(min = 3, max = 15)
	String nickname;

}
