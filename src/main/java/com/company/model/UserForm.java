package com.company.model;


import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class UserForm {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private List<Role> roles = new ArrayList<>();
    private MultipartFile avatar;
    private MultipartFile background;

    public UserForm() {
    }

    public UserForm(Long id, String name, String username, String email, String password, MultipartFile avatar, MultipartFile background) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.background = background;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    public MultipartFile getBackground() {
        return background;
    }

    public void setBackground(MultipartFile background) {
        this.background = background;
    }
}
