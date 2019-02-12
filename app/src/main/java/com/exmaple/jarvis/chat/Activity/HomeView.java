package com.exmaple.jarvis.chat.Activity;

import com.exmaple.jarvis.chat.Model.ChatMessageListItem;
import com.exmaple.jarvis.chat.Model.Message;

import java.util.List;

public interface HomeView {
    void setAdapter(List<ChatMessageListItem> msgList);
}
