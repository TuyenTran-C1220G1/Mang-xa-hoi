package com.company.repository;

import com.company.model.LikePost;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ILikeRepository extends CrudRepository<LikePost,Long> {

    Optional<LikePost> findLikePostByPostIdAndUserId(long postId,long userId);
}
