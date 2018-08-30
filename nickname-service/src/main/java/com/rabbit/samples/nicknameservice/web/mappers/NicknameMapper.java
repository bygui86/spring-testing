package com.rabbit.samples.nicknameservice.web.mappers;

import com.rabbit.samples.nicknameservice.persistence.entities.Nickname;
import com.rabbit.samples.nicknameservice.web.dtos.NicknameDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;


@Mapper(implementationPackage = "<PACKAGE_NAME>.impl")
public interface NicknameMapper {

	@Mappings({
			// no specific mappings
	})
	NicknameDto mapEntityToDto(final Nickname model);

	@InheritInverseConfiguration
	Nickname mapDtoToEntity(final NicknameDto dto);

}
