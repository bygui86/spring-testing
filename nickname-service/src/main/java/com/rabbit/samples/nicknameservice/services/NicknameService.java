package com.rabbit.samples.nicknameservice.services;

import com.rabbit.samples.nicknameservice.persistence.entities.Nickname;

import java.util.List;


public interface NicknameService {

	List<Nickname> getAll();

	Nickname getByEmployeeName(final String employeeName);

}
