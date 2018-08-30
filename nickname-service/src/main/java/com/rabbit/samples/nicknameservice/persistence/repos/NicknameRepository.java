package com.rabbit.samples.nicknameservice.persistence.repos;

import com.rabbit.samples.nicknameservice.persistence.entities.Nickname;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository("nicknameRepository")
@Transactional
public interface NicknameRepository extends JpaRepository<Nickname, Long> {

	Nickname findByEmployeeId(final Long employeeId);

}
