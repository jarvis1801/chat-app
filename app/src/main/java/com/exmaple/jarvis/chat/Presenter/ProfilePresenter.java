package com.exmaple.jarvis.chat.Presenter;

import android.util.Log;

import com.exmaple.jarvis.chat.Activity.LoginView;
import com.exmaple.jarvis.chat.Activity.ProfileView;
import com.exmaple.jarvis.chat.Model.User;
import com.exmaple.jarvis.chat.Presenter.Interface.ProfilePresenterInterface;
import com.exmaple.jarvis.chat.Retrofit.RetrofitFactory;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class ProfilePresenter implements ProfilePresenterInterface {
    private ProfileView mView;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    public ProfilePresenter(ProfileView view) {
        mView = view;
    }

    public void updateUser(String username, String displayname, String password, String email) {
        Disposable disposable = RetrofitFactory.getInstance().updateUser(username, displayname, password, email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(@NonNull User user) throws Exception {
                        mView.updateSuccess(user);
                        mView.dismissLoadingDialog();
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
