package com.exmaple.jarvis.chat.Model;

public class ChatRoomMessage extends Object {
    private String id;
    private String sender;
    private String message;
    private String groupId;
    private String group;
    private String posted_at;

    /**
     * No args constructor for use in serialization
     *
     */
    public ChatRoomMessage() {
    }

    /**
     *
     * @param message
     * @param sender
     * @param id
     * @param groupId
     * @param group
     * @param posted_at
     */
    public ChatRoomMessage(String id, String sender, String message, String groupId, String group, String posted_at) {
        super();
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.groupId = groupId;
        this.group = group;
        this.posted_at = posted_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getPostedAt() {
        return posted_at;
    }

    public void setPostedAt(String postedAt) {
        this.posted_at = postedAt;
    }

}