package com.exmaple.jarvis.chat.Presenter;

import android.util.Log;

import com.exmaple.jarvis.chat.Activity.HomeView;
import com.exmaple.jarvis.chat.Model.ChatMessageListItem;
import com.exmaple.jarvis.chat.Model.Message;
import com.exmaple.jarvis.chat.Model.User;
import com.exmaple.jarvis.chat.Presenter.Interface.HomePresenterInterface;
import com.exmaple.jarvis.chat.Retrofit.RetrofitFactory;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter implements HomePresenterInterface {
    private HomeView mView;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    public HomePresenter(HomeView homeView) {
        mView = homeView;
    }

    public void getChatMsgList(String username) {
        Disposable disposable = RetrofitFactory.getInstance().getChatMsgList(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ChatMessageListItem>>() {
                    @Override
                    public void accept(@NonNull List<ChatMessageListItem> msgList) throws Exception {
                        mView.setAdapter(msgList);
                        Log.e("test", "yes");
                        Log.e("test", msgList.toString());
                        for (int i = 0; i < msgList.size(); i++) {
                            Log.e("test", msgList.get(i).toString());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

        mDisposable.add(disposable);
    }
}
