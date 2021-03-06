package com.exmaple.jarvis.chat.Model;

public class Group {
    private String _id;
    private String user1;
    private String user2;

    /**
     * No args constructor for use in serialization
     *
     */
    public Group() {
    }

    /**
     *
     * @param _id
     * @param user2
     * @param user1
     */
    public Group(String _id, String user1, String user2) {
        super();
        this._id = _id;
        this.user1 = user1;
        this.user2 = user2;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

}