package com.exmaple.jarvis.chat.Presenter.Interface;

public interface ChatPresenterInterface {
    void getChatMsgById(String groupId);
    void postChatRoomMsg(String sender, String groupId, String group, String message);
    void postChatRoomGroup(String user1, String user2);
}
