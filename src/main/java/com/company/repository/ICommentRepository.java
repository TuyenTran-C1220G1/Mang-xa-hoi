package com.company.repository;

import com.company.model.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ICommentRepository extends PagingAndSortingRepository<Comment,Long> {
    Iterable<Comment> findAllByPostId(long id);
}
