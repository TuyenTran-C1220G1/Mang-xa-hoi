package com.company.service;

import com.company.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPostService extends IGeneralService<Post>{
    Iterable<Post> findAllByUserId(Long id);
    Page<Post> findAll(Pageable pageable);
}
