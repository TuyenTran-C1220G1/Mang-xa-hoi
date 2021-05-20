package com.company.service.impl;

import com.company.model.Relationship;
import com.company.model.User;
import com.company.repository.IRelationshipRepository;
import com.company.service.IRelationshipService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
@Service
public class RelationshipServiceImpl implements IRelationshipService {
    @Autowired
    IRelationshipRepository relationshipRepository;


    @Override
    public List<Relationship> findAllByUserAndStatus(User user, int status) {
        return relationshipRepository.findAllByUserAndStatus(user, status);
    }

    @Override
    public List<Relationship> findAllByUserFriendAndStatus(User userFriend, int status) {
        return relationshipRepository.findAllByUserFriendAndStatus(userFriend, status);
    }


}
