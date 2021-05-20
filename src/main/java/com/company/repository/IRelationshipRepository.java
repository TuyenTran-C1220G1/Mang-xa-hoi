package com.company.repository;


import com.company.model.Relationship;
import com.company.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.util.List;

@Repository
public interface IRelationshipRepository extends JpaRepository<Relationship, Long> {
    List<Relationship> findAllByUserAndStatus(User user, int status);
    List<Relationship> findAllByUserFriendAndStatus(User userFriend, int status);
}
