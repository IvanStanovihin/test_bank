package com.example.bank.dao.repository;

import com.example.bank.domain.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("from User u left join EmailData e on u.id=e.user.id where e.email=:email")
  Optional<User> findByEmail(String email);

}
