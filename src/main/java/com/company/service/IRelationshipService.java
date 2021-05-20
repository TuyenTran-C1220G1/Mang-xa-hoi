package com.company.service;

import com.company.model.Relationship;
import com.company.model.User;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface IRelationshipService {
    List<Relationship> findAllByUserAndStatus(User user, int status);
    List<Relationship> findAllByUserFriendAndStatus(User userFriend, int status);


}
