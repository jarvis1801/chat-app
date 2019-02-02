package com.exmaple.jarvis.chat.Presenter.Login;

import android.content.Context;

public class LoginPresenter implements LoginPresenterInterface {
    private Context mContext;

    @Override
    public void setView(Context context) {
        mContext = context;
    }
}
