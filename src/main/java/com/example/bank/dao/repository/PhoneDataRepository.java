package com.example.bank.dao.repository;

import com.example.bank.domain.model.PhoneData;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {


  Optional<PhoneData> findByPhone(String phone);

  @Modifying
  @Transactional
  @Query("delete from PhoneData p where p.user.id=:userId and p.phone=:phone")
  Integer deletePhoneByUserId(Long userId, String phone);

  @Modifying
  @Transactional
  @Query("update PhoneData p set p.phone=:newPhone where p.user.id=:userId and p.phone=:oldPhone")
  Integer updatePhoneByUserId(Long userId, String newPhone, String oldPhone);

}
