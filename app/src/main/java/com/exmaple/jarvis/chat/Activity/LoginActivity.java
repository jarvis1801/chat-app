package com.exmaple.jarvis.chat.Activity;import android.content.Intent;import android.os.Bundle;import android.text.Editable;import android.text.TextWatcher;import android.view.View;import android.widget.ImageView;import android.widget.Switch;import android.widget.TextView;import com.google.android.material.textfield.TextInputEditText;import com.google.android.material.textfield.TextInputLayout;import androidx.appcompat.app.AppCompatActivity;public class LoginActivity extends AppCompatActivity {    private ImageView img_back;    private TextView tv_remember_me, tv_forgot_password, tv_register_reminder;    private Switch switch_remember_me;    private TextInputLayout til_password, til_username;    private TextInputEditText et_password, et_username;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_login);        img_back = findViewById(R.id.img_back);        tv_remember_me = findViewById(R.id.tv_remember_me);        tv_forgot_password = findViewById(R.id.tv_forgot_password);        tv_register_reminder = findViewById(R.id.tv_register_reminder);        switch_remember_me = findViewById(R.id.switch_remember_me);        til_password = findViewById(R.id.til_password);        til_username = findViewById(R.id.til_username);        et_password = findViewById(R.id.et_password);        et_username = findViewById(R.id.et_username);        et_password.addTextChangedListener(new TextWatcher() {            @Override            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }            @Override            public void onTextChanged(CharSequence s, int start, int before, int count) {            }            @Override            public void afterTextChanged(Editable s) {                if (s.length() < 8)                    til_password.setError("Min character length is 8");                else                    til_password.setError(null);            }        });        et_username.addTextChangedListener(new TextWatcher() {            @Override            public void beforeTextChanged(CharSequence s, int start, int count, int after) {            }            @Override            public void onTextChanged(CharSequence s, int start, int before, int count) {            }            @Override            public void afterTextChanged(Editable s) {                if (s.length() < 8)                    til_username.setError("Min character length is 8");                else                    til_username.setError(null);            }        });        // OnClick        img_back.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                finish();            }        });        tv_remember_me.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                switch_remember_me.toggle();            }        });        tv_forgot_password.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                Intent i = new Intent(getApplicationContext(), ForgotPasswordActivity.class);                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);                startActivity(i);            }        });        tv_register_reminder.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);                startActivity(i);            }        });    }}