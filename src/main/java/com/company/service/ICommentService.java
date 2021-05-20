package com.company.service;

import com.company.model.Comment;

public interface ICommentService extends IGeneralService<Comment> {
    Iterable<Comment> findAllByPostId(Long id);
}
