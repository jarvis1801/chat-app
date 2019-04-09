package com.exmaple.jarvis.chat.Model;

import org.json.JSONObject;

public class ChatMessageListItem extends Object {
    private String _id;
    private String sender;
    private String message;
    private String group_id;
    private String posted_at;
    private Group group;
    private User other_user;

    public ChatMessageListItem(String id, String sender, String message,String groupId, String postedAt, Group group, User other_user) {
        this._id = id;
        this.sender = sender;
        this.message = message;
        this.group_id = groupId;
        this.posted_at = postedAt;
        this.group = group;
        this.other_user = other_user;
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

    public Group getGroup() {
        return this.group;
    }

    public User getOtherUser() {
        return this.other_user;
    }

    public String toString() {
        return this._id + "\n" + this.sender + "\n" + this.group_id + "\n" + this.message + "\n" + this.posted_at;
    }
}
