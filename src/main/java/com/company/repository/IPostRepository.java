package com.company.repository;

import com.company.model.Post;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPostRepository extends PagingAndSortingRepository<Post,Long> {
    Iterable<Post> findAllByUserId(Long id);
    Optional<Post> findById(Long id);
}
