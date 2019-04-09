package com.exmaple.jarvis.chat.Model;

public class Message extends Object {
    private String _id;
    private String sender;
    private String message;
    private String group_id;
    private String posted_at;

    public Message(String id, String sender, String message,String groupId, String postedAt) {
        this._id = id;
        this.sender = sender;
        this.message = message;
        this.group_id = groupId;
        this.posted_at = postedAt;
    }

    public String getId() {
        return this._id;
    }

    public String getSender() {
        return this.sender;
    }

    public String getGroupId() {
        return this.group_id;
    }

    public String getMessage() {
        return this.message;
    }

    public String getPostedAt() {
        return this.posted_at;
    }

    public String toString() {
        return this._id + "\n" + this.sender + "\n" + this.group_id + "\n" + this.message + "\n" + this.posted_at;
    }
}
