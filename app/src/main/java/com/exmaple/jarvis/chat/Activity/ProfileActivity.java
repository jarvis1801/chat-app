package com.exmaple.jarvis.chat.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.exmaple.jarvis.chat.Constant;
import com.exmaple.jarvis.chat.Model.User;
import com.exmaple.jarvis.chat.Presenter.ProfilePresenter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import okhttp3.RequestBody;

public class ProfileActivity extends AppCompatActivity implements ProfileView {
    private ImageView btn_back, profile_image;
    private TextView tv_display_name, tv_email, tv_username;
    private CardView cv_update;

    private TextInputLayout til_display_name, til_new_password, til_email;
    private TextInputEditText tiet_display_name, tiet_new_password, tiet_email;


    private String username, email, displayname, avatar_link, password;
    private Map<String, String> requestBodyData;

    private android.app.AlertDialog loadingDialog;

    private ProfilePresenter profilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilePresenter = new ProfilePresenter(this);

        setLoadingDialog();
        getUserInfo();
        initView();
        initViewEvent();
    }

    private void initView() {
        btn_back = findViewById(R.id.btn_back);
        tv_display_name = findViewById(R.id.tv_display_name);
        tv_email = findViewById(R.id.tv_email);
        tv_username = findViewById(R.id.tv_username);
        cv_update = findViewById(R.id.cv_update);
        profile_image = findViewById(R.id.profile_image);

        // text layout
        til_display_name = findViewById(R.id.til_display_name);
        til_new_password = findViewById(R.id.til_new_password);
        til_email = findViewById(R.id.til_email);

        tiet_display_name = findViewById(R.id.tiet_display_name);
        tiet_new_password = findViewById(R.id.tiet_new_password);
        tiet_email = findViewById(R.id.tiet_email);

        initData();
    }

    private void initData() {
        tv_username.setText(username);
        tv_display_name.setText(displayname);
        tv_email.setText(email);

        tiet_display_name.setText(displayname);
        tiet_email.setText(email);

        if (!avatar_link.equals("")) {
            Glide.with(this)
                    .load(avatar_link)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.placeholder)
                            .centerCrop())
                    .into(profile_image);
        }
    }

    private void initViewEvent() {
        cv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkField()) {
                    showLoadingDialog();

                    String temp_password = !tiet_new_password.getText().toString().equals("") ? tiet_new_password.getText().toString() : password;
                    String temp_email = !tiet_email.getText().toString().equals("") ? tiet_email.getText().toString() : email;
                    String temp_displayName = !tiet_display_name.getText().toString().equals("") ? tiet_display_name.getText().toString() : displayname;

                    updateUser(username, temp_displayName, temp_password, temp_email);
                } else {
                    Toasty.warning(getApplicationContext(), "Please change the information to update.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setLoadingDialog() {
        loadingDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage(getString(R.string.loading))
                .setCancelable(false)
                .build();
    }

    private void showLoadingDialog() {
        loadingDialog.show();
    }

    @Override
    public void dismissLoadingDialog() {
        loadingDialog.dismiss();
    }


    public void updateUser(String username, String displayname, String password, String email) {
        profilePresenter.updateUser(username, displayname, password, email);
    }

    private Boolean checkField() {
        if (!displayname.equals(tiet_display_name.getText().toString())
                || !tiet_new_password.getText().toString().equals("")
                || !email.equals(tiet_email.getText().toString())) {

            return true;
        }
        return false;
    }

    private void getUserInfo() {
        SharedPreferences prefs = getSharedPreferences("userInfo", MODE_PRIVATE);
        username = prefs.getString("username", "");
        password = prefs.getString("password", "");
        email = prefs.getString("email", "");
        displayname = prefs.getString("displayname", "");
        avatar_link = prefs.getString("avatar", "");
    }

    @Override
    public void updateSuccess(User user) {
        SharedPreferences prefs = getSharedPreferences("userInfo", MODE_PRIVATE);
        prefs.edit().putString("displayname", user.getDisplayName()).apply();
        prefs.edit().putString("password", user.getPassword()).apply();
        prefs.edit().putString("email", user.getEmail()).apply();
        Toasty.success(this, "Success update information.", Toasty.LENGTH_SHORT).show();
        finish();
    }
}
