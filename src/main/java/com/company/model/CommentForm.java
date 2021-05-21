package com.company.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Date;

public class CommentForm {

    private Long id;

    private User user;

    private Long postId;
    private Date createdAt;
    private String content;

    private int status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    private int likes;



    public CommentForm(Long postId, String content) {
        this.postId = postId;
        this.content = content;
    }

    public CommentForm() {
    }

    public CommentForm(String content) {
        this.content = content;
    }
}
