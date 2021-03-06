package com.exmaple.jarvis.chat.Presenter;

import android.util.Log;

import com.exmaple.jarvis.chat.Activity.ChatView;
import com.exmaple.jarvis.chat.Model.ChatMessageListItem;
import com.exmaple.jarvis.chat.Model.ChatRoomMessage;
import com.exmaple.jarvis.chat.Model.Group;
import com.exmaple.jarvis.chat.Model.User;
import com.exmaple.jarvis.chat.Presenter.Interface.ChatPresenterInterface;
import com.exmaple.jarvis.chat.Retrofit.RetrofitFactory;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class ChatPresenter implements ChatPresenterInterface {
    private ChatView mView;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    public ChatPresenter(ChatView addFriendView) {
        mView = addFriendView;
    }

    public void getChatMsgById(String groupId) {
        Disposable disposable = RetrofitFactory.getInstance().getChatRoomMsgList(groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ChatRoomMessage>>() {
                    @Override
                    public void accept(@NonNull List<ChatRoomMessage> msgList) throws Exception {
                        mView.setAdapter(msgList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

        mDisposable.add(disposable);
    }

    @Override
    public void postChatRoomMsg(String sender, String groupId, String group, String message) {
        final Disposable disposable = RetrofitFactory.getInstance().postChatRoomMsg(sender, groupId, group, message)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ChatRoomMessage>() {
                    @Override
                    public void accept(@NonNull ChatRoomMessage msg) throws Exception {
                        mView.getChatRoomMsgList();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

        mDisposable.add(disposable);
    }

    @Override
    public void postChatRoomGroup(String user1, String user2) {
        final Disposable disposable = RetrofitFactory.getInstance().postChatRoomGroup(user1, user2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Group>() {
                    @Override
                    public void accept(@NonNull Group group) throws Exception {
                        mView.setGroupId(group.getId());
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