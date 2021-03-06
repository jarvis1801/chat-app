package com.exmaple.jarvis.chat.Presenter;

import android.util.Log;

import com.exmaple.jarvis.chat.Activity.LoginView;
import com.exmaple.jarvis.chat.Activity.RegisterView;
import com.exmaple.jarvis.chat.Model.User;
import com.exmaple.jarvis.chat.Presenter.Interface.LoginPresenterInterface;
import com.exmaple.jarvis.chat.Presenter.Interface.RegisterPresenterInterface;
import com.exmaple.jarvis.chat.Retrofit.RetrofitFactory;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterPresenter implements RegisterPresenterInterface {
    private RegisterView mView;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    public RegisterPresenter(RegisterView view) {
        mView = view;
    }

    @Override
    public void postUser(Map<String, RequestBody> partMap, MultipartBody.Part avatar) {
        final Disposable disposable = RetrofitFactory.getInstance().postUser(partMap, avatar)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(@NonNull User user) throws Exception {
                        mView.dismissLoadingDialog();
                        mView.registerSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.dismissLoadingDialog();
                        String errMsg = throwable.getLocalizedMessage();
                        if (errMsg.contains("failed to connect")) {
                            mView.showDatabaseErrDialog();
                        } else {
                            mView.showUserExistDialog();
                        }
                        throwable.printStackTrace();
                    }
                });

        mDisposable.add(disposable);
    }
}

