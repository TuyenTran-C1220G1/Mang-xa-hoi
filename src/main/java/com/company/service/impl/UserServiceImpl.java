package com.company.service.impl;

import com.company.model.User;
import com.company.repository.IUserRepository;
import com.company.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository userRepository;

    @Override
    public User findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public User save(User user) {
        userRepository.save(user);
        return user;
    }
}
