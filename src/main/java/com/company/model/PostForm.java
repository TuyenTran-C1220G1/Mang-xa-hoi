package com.company.model;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PostForm {
    private long id;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private User user;
    private List<MultipartFile> images;
    private int likes;
    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
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

    public PostForm() {
    }

    public PostForm(long id, String title, String content, LocalDateTime createAt, User user, List<MultipartFile> images, int likes, int status) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createAt = createAt;
        this.user = user;
        this.images = images;
        this.likes = likes;
        this.status = status;
    }
}
