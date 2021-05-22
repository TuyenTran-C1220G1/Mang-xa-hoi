package com.company.service;

import com.company.model.Image;
import com.company.model.Post;
import com.company.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IPostService extends IGeneralService<Post>{
    Iterable<Post> findAllByUserId(Long id);
    Page<Post> findAll(Pageable pageable);
    Optional<Post> findById(Long id);
    Page<Post> findAllByStatusOrderByCreatedAtDesc(Pageable pageable,int status);
    Page<Post> findAllByUserIdOrderByCreatedAtDesc(Pageable pageable,Long id);
    void remove(Long id);

}
