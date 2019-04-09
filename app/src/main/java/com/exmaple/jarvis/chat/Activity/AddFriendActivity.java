package com.exmaple.jarvis.chat.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.exmaple.jarvis.chat.Model.User;
import com.exmaple.jarvis.chat.Presenter.AddFriendPresenter;
import com.exmaple.jarvis.chat.RecyclerView.AddFriendAdapter;
import com.exmaple.jarvis.chat.RecyclerView.ChatMsgRecyclerViewAdapter;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddFriendActivity extends AppCompatActivity implements AddFriendView {
    private RecyclerView rv_add_friend;
    private AddFriendAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String username;

    private AddFriendPresenter addFriendPresenter;

    private CardView cv_refresh_user_list;

    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        setViewElement();
        setViewEvent();
        initRecyclerView();
        getUsername();
        setPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getRandomUserList();
    }

    private void setViewElement() {
        rv_add_friend = findViewById(R.id.rv_add_friend);

        cv_refresh_user_list = findViewById(R.id.cv_refresh_user_list);

        img_back = findViewById(R.id.img_back);
    }

    private void setViewEvent() {
        cv_refresh_user_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRandomUserList();
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecyclerView() {
        rv_add_friend.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 2);
//        mLayoutManager = new LinearLayoutManager(this);
        rv_add_friend.setLayoutManager(mLayoutManager);
    }

    private void setPresenter() {
        addFriendPresenter = new AddFriendPresenter(this);
    }

    private void getUsername() {
        SharedPreferences prefs = getSharedPreferences("userInfo", MODE_PRIVATE);
        username = prefs.getString("username", "");
    }

    @Override
    public void setAdapter(List<User> userList) {
        mAdapter = new AddFriendAdapter(this, userList);
        rv_add_friend.setAdapter(mAdapter);
    }

    private void getRandomUserList() {
        addFriendPresenter.getRandomUserList(username);
    }
}


