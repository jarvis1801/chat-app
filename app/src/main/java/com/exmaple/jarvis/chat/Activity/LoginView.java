package com.exmaple.jarvis.chat.Activity;

import com.exmaple.jarvis.chat.Model.User;

public interface LoginView {
    void login(User user);
    void showErrorUsername();
    void showDatabaseErrorDialog();
}
