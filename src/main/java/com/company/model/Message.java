package com.company.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Date;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_friend_id")
    private User userFriend;
    private String content;
    private Date createdAt;
    @Min(0)
    @Max(1)
    private int status;

    public Message() {
    }

    public Message(Long id, User user, User userFriend, String content, Date createdAt, @Min(0) @Max(1) int status) {
        this.id = id;
        this.user = user;
        this.userFriend = userFriend;
        this.content = content;
        this.createdAt = createdAt;
        this.status = status;
    }

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

    public User getUserFriend() {
        return userFriend;
    }

    public void setUserFriend(User userFriend) {
        this.userFriend = userFriend;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
