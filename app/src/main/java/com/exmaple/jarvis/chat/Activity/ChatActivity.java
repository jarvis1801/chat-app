package com.exmaple.jarvis.chat.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.exmaple.jarvis.chat.Constant;
import com.exmaple.jarvis.chat.Model.ChatMessageListItem;
import com.exmaple.jarvis.chat.Model.ChatRoomMessage;
import com.exmaple.jarvis.chat.Presenter.ChatPresenter;
import com.exmaple.jarvis.chat.Presenter.HomePresenter;
import com.exmaple.jarvis.chat.RecyclerView.ChatMsgRecyclerViewAdapter;
import com.exmaple.jarvis.chat.RecyclerView.ChatRoomMsgAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.RequestBody;

public class ChatActivity extends AppCompatActivity implements ChatView {
    private RecyclerView rv_chat_msg_list;
    private ChatRoomMsgAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ChatPresenter chatPresenter;

    private String username, groupId = null, user2_username, user2_displayname, user2_avatar;

    private TextView tv_username;

    private ImageView img_avatar, img_back, img_send;

    private EditText et_message;

    private List<ChatRoomMessage> mMsgList = new ArrayList();

    private CardView cv_send_msg;

    private boolean hasGroupId = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        groupId = getIntent().getStringExtra("group_id");
        user2_username = getIntent().getStringExtra("user2_username");
        user2_displayname = getIntent().getStringExtra("user2_displayname");
        user2_avatar = getIntent().getStringExtra("user2_avatar");

        if (groupId != null) {
            hasGroupId = true;
        }

        setViewElement();
        setViewEvent();
        initRecyclerView();
        getUsername();
        setPresenter();
        getChatRoomMsgList();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setViewEvent() {
        Glide.with(this)
                .load(user2_avatar)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .fitCenter())
                .into(img_avatar);

        tv_username.setText(user2_displayname);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_message.getText().length() > 0) {
                    if (hasGroupId) {
                        postChatRoomMsg();
                    } else {
                        postChatRoomGroup();
                    }
                }
            }
        });

        et_message.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mMsgList.size() > 0) {
                    rv_chat_msg_list.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rv_chat_msg_list.scrollToPosition(mMsgList.size() - 1);
                        }
                    }, 100);
                }
                return false;
            }
        });
    }

    private void initRecyclerView() {
        rv_chat_msg_list.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        rv_chat_msg_list.setLayoutManager(mLayoutManager);
    }


    private void setViewElement() {
        rv_chat_msg_list = findViewById(R.id.rv_chat_msg_list);

        tv_username = findViewById(R.id.tv_username);

        img_avatar = findViewById(R.id.img_avatar);

        img_back = findViewById(R.id.img_back);

        img_send = findViewById(R.id.img_send);

        et_message = findViewById(R.id.et_message);

        cv_send_msg = findViewById(R.id.cv_send_msg);
    }

    private void setPresenter() {
        chatPresenter = new ChatPresenter(this);
    }

    public void getChatRoomMsgList() {
        chatPresenter.getChatMsgById(groupId);
    }

    @Override
    public void setAdapter(List<ChatRoomMessage> msgList) {
        mAdapter = new ChatRoomMsgAdapter(getApplicationContext(), msgList, username);
        rv_chat_msg_list.setAdapter(mAdapter);
        rv_chat_msg_list.smoothScrollToPosition(msgList.size() - 1);
        mMsgList = msgList;
    }

    private void getUsername() {
        SharedPreferences prefs = getSharedPreferences("userInfo", MODE_PRIVATE);
        username = prefs.getString("username", "");
    }

    private void postChatRoomMsg() {
        chatPresenter.postChatRoomMsg(username, groupId, groupId, et_message.getText().toString());
        et_message.setText("");
    }

    private void postChatRoomGroup() {
        chatPresenter.postChatRoomGroup(username, user2_username);
    }

    public void setGroupId(String groupId) {
        hasGroupId = true;
        this.groupId = groupId;
        // post again
        postChatRoomMsg();

    }
}

