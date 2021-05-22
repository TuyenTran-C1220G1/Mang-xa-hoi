package com.company.model;


import org.hibernate.annotations.NaturalId;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class UserForm {
    private Long id;
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    private String username;

    @Email
    private String email;
    private String password;
    private List<Role> roles = new ArrayList<>();
    private MultipartFile avatar;
    private MultipartFile background;
    private String description;
    private Date dob;

//    @Pattern(regexp = "(0[1-9])+([0-9]{8})", message = "Input 10 digits starts with 0")
    private String phone;
    private String country;
    private String gender;

    public UserForm() {
    }

    public UserForm(Long id, String name, String username, String email, String password, MultipartFile avatar, MultipartFile background, String description, Date dob, String phone, String country, String gender) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.background = background;
        this.description = description;
        this.dob = dob;
        this.phone = phone;
        this.country = country;
        this.gender = gender;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
