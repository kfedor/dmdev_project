package com.github.nutscoding.dmdev.project.entity;

public class Users {

    private Integer id;
    private String login;
    private String password;
    private Integer userTypeId;
    private UserDetails userDetails;

    public Users(Integer id, String login, String password, Integer userTypeId, UserDetails userDetails) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.userTypeId = userTypeId;
        this.userDetails = userDetails;
    }

    public Users() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", userTypeId=" + userTypeId +
                ", userDetails=" + userDetails +
                '}';
    }
}
