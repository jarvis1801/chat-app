package com.exmaple.jarvis.chat.Presenter;

import android.util.Log;

import com.exmaple.jarvis.chat.Activity.AddFriendView;
import com.exmaple.jarvis.chat.Activity.HomeView;
import com.exmaple.jarvis.chat.Model.ChatMessageListItem;
import com.exmaple.jarvis.chat.Model.User;
import com.exmaple.jarvis.chat.Presenter.Interface.AddFriendPresenterInterface;
import com.exmaple.jarvis.chat.Retrofit.RetrofitFactory;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddFriendPresenter implements AddFriendPresenterInterface {
    private AddFriendView mView;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    public AddFriendPresenter(AddFriendView addFriendView) {
        mView = addFriendView;
    }

    public void getRandomUserList(String username) {
        Disposable disposable = RetrofitFactory.getInstance().getRandomUserList(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(@NonNull List<User> userList) throws Exception {
                        mView.setAdapter(userList);
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
