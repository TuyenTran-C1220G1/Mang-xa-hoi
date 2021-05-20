package com.company.service;

import com.company.model.Post;

public interface IPostService extends IGeneralService<Post>{
    Iterable<Post> findAllByUserId(Long id);
}
