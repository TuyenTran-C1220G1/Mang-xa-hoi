package com.company.service.impl;

import com.company.model.LikePost;
import com.company.repository.ILikeRepository;
import com.company.service.ILikePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LikePostService implements ILikePostService {
    @Autowired
    ILikeRepository likeRepository;
    @Override
    public Iterable<LikePost> findAll() {
        return null;
    }

    @Override
    public Optional<LikePost> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(LikePost likePost) {
        likeRepository.save(likePost);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Optional<LikePost> findByPostIdAndUserId(Long postId, Long userId) {
        return likeRepository.findLikePostByPostIdAndUserId(postId,userId);
    }
}
