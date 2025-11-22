package com.ryder.ryder.Users.repositories;

import com.ryder.ryder.Users.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<Users> findByEmail(String email);

}
