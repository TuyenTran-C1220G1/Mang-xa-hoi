package com.company.service.impl;

import com.company.model.Relationship;
import com.company.model.User;
import com.company.repository.IRelationshipRepository;
import com.company.service.IRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RelationshipServiceImpl implements IRelationshipService {
    @Autowired
    IRelationshipRepository relationshipRepository;
    @Override
    public List<Relationship> findAllByUserAndStatus(User user, int status) {
        return relationshipRepository.findAllByUserAndStatus(user,status);
    }

    @Override
    public List<Relationship> findAllByUserFriendAndStatus(User userFriend, int status) {
        return relationshipRepository.findAllByUserFriendAndStatus(userFriend,status);
    }

    @Override
    public List<Relationship> findAllByUser(User user) {
        return relationshipRepository.findAllByUser(user);
    }

    @Override
    public List<Relationship> findAllByUserFriend(User userFriend) {
        return relationshipRepository.findAllByUserFriend(userFriend);
    }

    @Override
    public Relationship save(Relationship relationship) {
        return relationshipRepository.save(relationship);
    }



    @Override
    public Optional<Relationship> findById(Long id) {
        return relationshipRepository.findById(id);
    }

}
