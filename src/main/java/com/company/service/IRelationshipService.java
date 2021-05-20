package com.company.service;

import com.company.model.Relationship;
import com.company.model.User;

import java.util.List;
import java.util.Optional;

public interface IRelationshipService {
    List<Relationship> findAllByUserAndStatus(User user, int status);
    List<Relationship> findAllByUserFriendAndStatus(User userFriend, int status);
    List<Relationship> findAllByUser(User user);
    List<Relationship> findAllByUserFriend(User userFriend);
    Relationship save(Relationship relationship);
    Optional<Relationship> findById(Long id);
}
