package com.company.service.impl;

import com.company.model.Comment;
import com.company.service.ICommentService;

import java.util.Optional;

public class CommentService implements ICommentService {
    @Override
    public Iterable<Comment> findAll() {
        return null;
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Comment comment) {

    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Iterable<Comment> findAllByPostId(Long id) {
        return null;
    }
}
