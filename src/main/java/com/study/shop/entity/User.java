package com.study.shop.entity;

import com.study.shop.security.entity.UserRole;

public class User {
    String name;
    String password;
    String salt;
    UserRole userRole;

    public User(String name, String password, String salt, UserRole userRole) {
        this.name = name;
        this.password = password;
        this.userRole = userRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getSalt() { return salt; }

    public void setSalt(String salt) { this.salt = salt; }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
