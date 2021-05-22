package com.company.service.impl;

import com.company.model.Image;
import com.company.model.User;
import com.company.repository.IImageRepository;
import com.company.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ImageService implements IImageService {
    @Autowired
    IImageRepository iImageRepository;

    @Override
    public Iterable<Image> findAll() {
        return null;
    }

    @Override
    public Optional<Image> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Image image) {
        iImageRepository.save(image);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Iterable<Image> findAllByUserId(Long id) {
        return iImageRepository.findAllByUserId(id);
    }
}
