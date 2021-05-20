package com.company.service.impl;

import com.company.model.Post;
import com.company.repository.IPostRepository;
import com.company.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService implements IPostService {
    @Autowired
    IPostRepository postRepository;


    @Override
    public Iterable<Post> findAll() {
        return null;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public void remove(Long id) {
        postRepository.deleteById(id);
    }


    @Override
    public Iterable<Post> findAllByUserId(Long id) {
        return postRepository.findAllByUserId(id);
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
}
