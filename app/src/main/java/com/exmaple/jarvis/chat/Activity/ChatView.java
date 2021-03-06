package com.exmaple.jarvis.chat.Activity;

import com.exmaple.jarvis.chat.Model.ChatMessageListItem;
import com.exmaple.jarvis.chat.Model.ChatRoomMessage;
import com.exmaple.jarvis.chat.Model.User;

import java.util.List;

public interface ChatView {
    void setAdapter(List<ChatRoomMessage> msgList);
    void getChatRoomMsgList();
    void setGroupId(String groupId);
}

