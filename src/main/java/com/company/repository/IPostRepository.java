package com.company.repository;

import com.company.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPostRepository extends PagingAndSortingRepository<Post,Long> {
    Iterable<Post> findAllByUserId(Long id);
    Page<Post> findAllByUserIdOrderByCreatedAtDesc(Pageable pageable,Long id);
    Optional<Post> findById(Long id);
    Page<Post> findAllByStatusOrderByCreatedAtDesc(Pageable pageable,int status);
    List<Post> findAllByStatus(int status);
}
