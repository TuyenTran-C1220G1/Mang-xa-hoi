package com.company.service;

import com.company.model.User;

public interface IUserService {
    User findByUsername(String userName);
    User save(User user);
}
