package com.company.service.impl;

import com.company.model.Image;
import com.company.model.Post;
import com.company.model.User;
import com.company.repository.IPostRepository;
import com.company.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public Page<Post> findAllByStatusOrderByCreatedAtDesc(Pageable pageable, int status) {
        return postRepository.findAllByStatusOrderByCreatedAtDesc(pageable,status);
    }

    @Override
    public Page<Post> findAllByUserIdOrderByCreatedAtDesc(Pageable pageable, Long id) {
        return postRepository.findAllByUserIdOrderByCreatedAtDesc(pageable,id);
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
    public List<Post> findAllByStatus(int status) {
        return postRepository.findAllByStatus(status);
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
