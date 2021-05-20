package com.company.repository;

import com.company.model.Relationship;
import com.company.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IRelationshipRepository extends JpaRepository<Relationship, Long> {
    List<Relationship> findAllByUserAndStatus(User user, int status);
    List<Relationship> findAllByUserFriendAndStatus(User userFriend, int status);
    Optional<Relationship> findAllByUserAndUserFriend(User user,User userFriend);
    List<Relationship> findAllByUser(User user);
    List<Relationship> findAllByUserFriend(User userFriend);

}
