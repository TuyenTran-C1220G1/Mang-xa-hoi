package com.company.repository;

import com.company.model.Post;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostRepository extends PagingAndSortingRepository<Post,Long> {
    public Iterable<Post> findAllByUserId(Long id);
}
