package com.exmaple.jarvis.chat.Model;public class User extends Object {    private String mId;    private String mUsername;    private String mPassword;    private String mLastname;    private String mFirstname;    private String mUserType;    public User(String id, String username, String password, String lastname, String firstname, String userType) {        mId = id;        mUsername = username;        mPassword = password;        mLastname = lastname;        mFirstname = firstname;        mUserType = userType;    }    public String getId() {        return mId;    }    public String getUsername() {        return mUsername;    }    public String getPassword() {        return mPassword;    }    public String getLastname() {        return mLastname;    }    public String getFirstname() {        return mFirstname;    }    public String getUserType() {        return mUserType;    }}