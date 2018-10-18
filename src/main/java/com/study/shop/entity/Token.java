package com.study.shop.entity;

public class Token {
    private String value;
    private String userName;
    private String role;

    public Token() {
        super();
    }

    public Token(String value, String userName, String role) {
        super();

        this.value = value;
        this.userName = userName;
        this.role = role;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
