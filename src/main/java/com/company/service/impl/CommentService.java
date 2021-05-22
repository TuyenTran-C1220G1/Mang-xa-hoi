package com.company.service.impl;

import com.company.model.Comment;
import com.company.repository.ICommentRepository;
import com.company.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CommentService implements ICommentService {
    @Autowired
    ICommentRepository repository;

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
        repository.save(comment);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Iterable<Comment> findAllByPostId(Long id) {
        return repository.findAllByPostId(id);
    }
}

