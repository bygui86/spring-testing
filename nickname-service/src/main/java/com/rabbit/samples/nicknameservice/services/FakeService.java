package com.rabbit.samples.nicknameservice.services;

import com.rabbit.samples.nicknameservice.persistence.entities.Nickname;


public interface FakeService {

	String getFakeString();

	String postFakeString(final String comment);

	Nickname getFakeNickname(final String employeeName);

}
