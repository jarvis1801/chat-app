package com.exmaple.jarvis.chat.Activity;

import com.exmaple.jarvis.chat.Model.User;

import java.util.Map;

import okhttp3.RequestBody;

public interface ProfileView {
    void updateSuccess(User user);
    void dismissLoadingDialog();
}
