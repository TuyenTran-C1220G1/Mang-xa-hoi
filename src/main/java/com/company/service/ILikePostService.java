package com.company.service;

import com.company.model.LikePost;

import java.util.Optional;

public interface ILikePostService extends IGeneralService<LikePost> {

    Optional<LikePost> findByPostIdAndUserId(Long postId,Long userId);
}
