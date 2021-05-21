package com.company.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tittle;
    private String content;
    private LocalDateTime createdAt;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "post_image",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<Image> images = new HashSet<>();
    private int likes;
    @Min(0)
    @Max(1)
    private int status;

    public Post() {
    }

    public Post(Long id, String tittle, String content, LocalDateTime createdAt, User user, Set<Image> images, int likes, int status) {
        this.id = id;
        this.tittle = tittle;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        this.images = images;
        this.likes = likes;
        this.status = status;
    }

    public Post(String tittle, String content, LocalDateTime createdAt, User user, Set<Image> images, int likes, @Min(0) @Max(1) int status) {
        this.tittle = tittle;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        this.images = images;
        this.likes = likes;
        this.status = status;
    }

    public Post(String tittle, String content, LocalDateTime createdAt, User user, Set<Image> images, int status) {
        this.tittle = tittle;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        this.images = images;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSimpleDate(){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDateTime = createdAt.format(formatter);
            return formattedDateTime;
        }catch (Exception e){
            return null;
        }
    }
}
