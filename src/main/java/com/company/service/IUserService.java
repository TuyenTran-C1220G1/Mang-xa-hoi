package com.company.service;

import com.company.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User findByUsername(String userName);
    Optional<User> findById(Long id);
    User save(User user);
    Boolean existsByUsername(String userName);
    Boolean existsByEmail(String email);
    List<User> findAll();
}
