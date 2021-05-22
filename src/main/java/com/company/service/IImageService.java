package com.company.service;

import com.company.model.Image;
import com.company.model.User;
import org.springframework.stereotype.Service;

@Service
public interface IImageService extends IGeneralService<Image> {
    Iterable<Image> findAllByUserId(Long id);

}
