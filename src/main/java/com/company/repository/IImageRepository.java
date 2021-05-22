package com.company.repository;

import com.company.model.Image;
import com.company.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImageRepository extends JpaRepository<Image,Long> {
    Iterable<Image> findAllByUserId(Long id);
}
