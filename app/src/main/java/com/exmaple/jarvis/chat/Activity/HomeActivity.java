package com.exmaple.jarvis.chat.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.exmaple.jarvis.chat.Model.ChatMessageListItem;
import com.exmaple.jarvis.chat.Model.Message;
import com.exmaple.jarvis.chat.Model.User;
import com.exmaple.jarvis.chat.Presenter.HomePresenter;
import com.exmaple.jarvis.chat.RecyclerView.ChatMsgRecyclerViewAdapter;
import com.exmaple.jarvis.chat.Util.RecyclerItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeView, NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView rv_chat_list;
    private ChatMsgRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ProgressBar pb_loading;

    private DrawerLayout drawer_layout;
    private NavigationView nav_view;
    private ImageView img_current_avatar, img_navigate;
    private TextView tv_username, tv_email;

    private FloatingActionButton fb_add_friend;

    private HomePresenter homePresenter;

    private String username, email, displayname, avatar_link;

    private Boolean doubleBackToExitPressedOnce = false;

    private AlertDialog db_error_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setViewElement();
        setViewEvent();
        initRecyclerView();
        getUserInfo();
        initUserInfoUI();
        setPresenter();
//        getChatMsgList();
    }

    @Override
    public void onResume(){
        super.onResume();

        getChatMsgList();
        getUserInfo();
        initUserInfoUI();
    }

    private void setViewEvent() {
        fb_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddFriendActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        });

        img_navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_layout.openDrawer(Gravity.LEFT);
            }
        });
    }

    private void initRecyclerView() {
        rv_chat_list.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        rv_chat_list.setLayoutManager(mLayoutManager);
        DividerItemDecoration itemDecor = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        rv_chat_list.addItemDecoration(itemDecor);
    }

    private void setPresenter() {
        homePresenter = new HomePresenter(this);
    }

    private void setViewElement() {
        rv_chat_list = findViewById(R.id.rv_chat_list);

        fb_add_friend = findViewById(R.id.fb_add_friend);

        img_navigate = findViewById(R.id.img_navigate);

        pb_loading = findViewById(R.id.pb_loading);

        // nav bar
        drawer_layout = findViewById(R.id.drawer_layout);

        nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);

        img_current_avatar = nav_view.getHeaderView(0).findViewById(R.id.img_current_avatar);
        tv_username = nav_view.getHeaderView(0).findViewById(R.id.tv_username);
        tv_email = nav_view.getHeaderView(0).findViewById(R.id.tv_email);
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(Gravity.LEFT);

        } else if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            moveTaskToBack(true);
            finishAffinity();
        } else {

            doubleBackToExitPressedOnce = true;
            Toasty.info(this, getString(R.string.close_app_hint), Toast.LENGTH_SHORT, true).show();


            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 1000);
        }
    }

//    public void test(View view) {
//        logout();
//    }

    private void logout() {
        SharedPreferences settings = getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.clear().apply();

        Intent i = new Intent(this, StartActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void getChatMsgList() {
        homePresenter.getChatMsgList(username);
    }

    private void getUserInfo() {
        SharedPreferences prefs = getSharedPreferences("userInfo", MODE_PRIVATE);
        username = prefs.getString("username", "");
        email = prefs.getString("email", "");
        displayname = prefs.getString("displayname", "");
        avatar_link = prefs.getString("avatar", "");
    }

    private void initUserInfoUI() {
        if (!displayname.equals("")) {
            tv_username.setText(displayname);
        }

        if (!email.equals("")) {
            tv_email.setText(email);
        }

        if (!avatar_link.equals("")) {
            Glide.with(this)
                    .load(avatar_link)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.placeholder)
                            .centerCrop())
                    .into(img_current_avatar);
        }
    }

    @Override
    public void setAdapter(List<ChatMessageListItem> msgList) {
        mAdapter = new ChatMsgRecyclerViewAdapter(getApplicationContext(), msgList, username);
        rv_chat_list.setAdapter(mAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_profile:
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                break;

            case R.id.nav_settings:
                Toasty.warning(this, "Incoming soon.", Toasty.LENGTH_SHORT).show();
                break;

            case R.id.nav_logout:
                logout();
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    public void showDatabaseErrorDialog() {
        db_error_dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.connect_db_failed))
                .setMessage(getString(R.string.connect_db_failed_content))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db_error_dialog.dismiss();
                        db_error_dialog = null;
                    }
                })
                .create();
        db_error_dialog.show();
    }

    public void hideProgressBar() {
        pb_loading.setVisibility(View.GONE);
    }
}

