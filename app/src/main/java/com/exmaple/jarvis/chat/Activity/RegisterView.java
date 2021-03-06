package com.exmaple.jarvis.chat.Activity;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface RegisterView {
    void register(Map<String, RequestBody> requestBody, MultipartBody.Part avatar);
    void showUserExistDialog();
    void showDatabaseErrDialog();
    void dismissLoadingDialog();
    void registerSuccess();
}
