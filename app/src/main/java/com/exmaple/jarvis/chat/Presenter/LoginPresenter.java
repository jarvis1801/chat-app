package com.exmaple.jarvis.chat.Presenter;

import android.util.Log;

import com.exmaple.jarvis.chat.Activity.LoginView;
import com.exmaple.jarvis.chat.Model.User;
import com.exmaple.jarvis.chat.Presenter.Interface.LoginPresenterInterface;
import com.exmaple.jarvis.chat.Retrofit.RetrofitFactory;

import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class LoginPresenter implements LoginPresenterInterface {
    private LoginView mView;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    public LoginPresenter(LoginView view) {
        mView = view;
    }

    public void getUser(String username) {
        Disposable disposable = RetrofitFactory.getInstance().getUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(@NonNull User user) throws Exception {
                        mView.login(user);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if (throwable instanceof SocketTimeoutException) {
                            mView.showDatabaseErrorDialog();
                        } else if (throwable instanceof HttpException) {
                            mView.showErrorUsername();
                        }
                        throwable.printStackTrace();
                    }
                });

        mDisposable.add(disposable);
    }
}
