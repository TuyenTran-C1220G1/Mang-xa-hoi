package com.company.repository;

import com.company.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User,Long> {
    User findByUsername(String userName);
    Boolean existsByUsername(String userName);
    Boolean existsByEmail(String email);
}
