package com.exmaple.jarvis.chat.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.exmaple.jarvis.chat.Listener.EditTextListener;
import com.exmaple.jarvis.chat.Model.User;
import com.exmaple.jarvis.chat.Presenter.LoginPresenter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class LoginActivity extends AppCompatActivity implements LoginView {
//    private ImageView img_back;
    private TextView tv_remember_me, tv_forgot_password, tv_register_reminder;
    private Switch switch_remember_me;
    private TextInputLayout til_password, til_username;
    private TextInputEditText et_password, et_username;
    private CardView cv_login;

    private LoginPresenter loginPresenter;

    private AlertDialog db_error_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setViewElement();
        setViewElementEvent();

        setPresenter();
    }

    private void setPresenter() {
        loginPresenter = new LoginPresenter(this);
    }

    private void setViewElement() {
//        img_back = findViewById(R.id.img_back);

        tv_remember_me = findViewById(R.id.tv_remember_me);
        tv_forgot_password = findViewById(R.id.tv_forgot_password);
        tv_register_reminder = findViewById(R.id.tv_register_reminder);

        switch_remember_me = findViewById(R.id.switch_remember_me);

        cv_login = findViewById(R.id.cv_login);

        til_password = findViewById(R.id.til_password);
        til_username = findViewById(R.id.til_username);
        et_password = findViewById(R.id.et_password);
        et_username = findViewById(R.id.et_username);
    }

    private void setViewElementEvent() {
//        et_password.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() < 8)
//                    til_password.setError(getString(R.string.min_char_length));
//                else
//                    til_password.setError(null);
//            }
//        });

        et_username.addTextChangedListener(new EditTextListener(getApplicationContext(), til_username, "general"));
        et_password.addTextChangedListener(new EditTextListener(getApplicationContext(), til_password, "general"));

//        et_username.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() < 8)
//                    til_username.setError(getString(R.string.min_char_length));
//                else
//                    til_username.setError(null);
//            }
//        });

        // OnClick

//        img_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        tv_remember_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_remember_me.toggle();
            }
        });

        tv_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        tv_register_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        });

        cv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                if (et_username.getText().length() < 8) {
                    til_username.setError(getString(R.string.min_char_length));
                    isValid = false;
                }

                if (et_password.getText().length() < 8) {
                    til_password.setError(getString(R.string.min_char_length));
                    isValid = false;
                }

                if (isValid)
                    loginPresenter.getUser(et_username.getText().toString());
            }
        });
    }

    @Override
    public void login(User user) {
        String userObjPW = user.getPassword();
        if (et_password.getText().toString().equals(userObjPW)) {
            savePreferences(user);
            loginStartActivity();
        } else {
            til_password.setError(getString(R.string.error_password));
        }
    }

    @Override
    public void showErrorUsername() {
        til_username.setError(getString(R.string.username_not_exist));
    }

    private void savePreferences(User user) {
        SharedPreferences settings = getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.clear();

        // Edit and commit
        editor.putString("username", user.getUsername());
        editor.putString("password", user.getPassword());
        editor.putString("email", user.getEmail());
        editor.putString("displayname", user.getDisplayName());
        editor.putString("avatar", user.getAvatar());

        editor.putBoolean("isLoggedIn", true);
        editor.putBoolean("isRememberMe", switch_remember_me.isChecked());
        editor.apply();
    }

    private void loginStartActivity() {
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
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
}

