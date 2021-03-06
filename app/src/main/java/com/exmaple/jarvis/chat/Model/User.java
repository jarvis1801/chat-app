package com.exmaple.jarvis.chat.Model;

public class User extends Object {
    private String id;
    private String username;
    private String password;
    private String displayName;
    private String userType;
    private String avatar;
    private String email;

    public User(String id, String username, String password, String displayName, String userType, String avatar, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.userType = userType;
        this.avatar = avatar;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getUserType() {
        return this.userType;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getEmail() {
        return this.email;
    }
}
