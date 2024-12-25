package com.berkaytell.repository;

import com.berkaytell.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserNameAndIsDeletedFalse(String userName);
}
