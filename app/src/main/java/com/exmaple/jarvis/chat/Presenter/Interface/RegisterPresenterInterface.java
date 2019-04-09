package com.exmaple.jarvis.chat.Presenter.Interface;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;

public interface RegisterPresenterInterface {
    void postUser(Map<String, RequestBody> partMap, MultipartBody.Part avatar);
}
