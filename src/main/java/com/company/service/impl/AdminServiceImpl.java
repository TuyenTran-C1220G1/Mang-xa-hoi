package com.company.service.impl;

import com.company.model.User;
import com.company.repository.IAdminRepository;
import com.company.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired
    IAdminRepository adminRepository;

    @Override
    public Iterable<User> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    public void save(User user) {
        adminRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        adminRepository.deleteById(id);
    }
}
