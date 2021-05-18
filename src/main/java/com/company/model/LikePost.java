package com.company.model;

import javax.persistence.*;

@Entity

public class LikePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne(targetEntity=User.class)
    @JoinColumn(name = "user_id")
    private User user;
    private int isLike;

    public LikePost() {
    }

    public LikePost(Long id, Post post, User user, int isLike) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.isLike = isLike;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }
}
