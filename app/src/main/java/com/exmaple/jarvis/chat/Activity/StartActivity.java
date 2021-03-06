package com.exmaple.jarvis.chat.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exmaple.jarvis.chat.ImageSlider.Adapter.StartInfoAdapter;
import com.exmaple.jarvis.chat.ImageSlider.CustomViewPager;
import com.uniquestudio.library.CircleCheckBox;

import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import static android.content.Context.MODE_PRIVATE;

public class StartActivity extends AppCompatActivity {
    private CustomViewPager vp_image_slider;
    private LinearLayout dots_container;
    private TextView tv_login, tv_tnc;
    private CardView cv_login;
    private AlertDialog tnc_dialog;
    private CircleCheckBox cb_tnc;
    private boolean isLoggedIn, isRememberMe, isAgreedTNC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLoggedIn();
        if (!isLoggedIn || !isRememberMe) {
            checkAgreedTNC();
        }
        if (!isLoggedIn || !isRememberMe && !isAgreedTNC) {
            setContentView(R.layout.activity_start);
            initView();

            setViewElementEvent();
        }
    }

    private void checkAgreedTNC() {
        SharedPreferences prefs = getSharedPreferences("tnc", MODE_PRIVATE);
        isAgreedTNC = prefs.getBoolean("agree", false);
        if (isAgreedTNC) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }

    private void checkLoggedIn() {
        SharedPreferences prefs = getSharedPreferences("userInfo", MODE_PRIVATE);
        isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        isRememberMe = prefs.getBoolean("isRememberMe", false);
        if (isLoggedIn && isRememberMe) {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }

    private void setViewElementEvent() {
        vp_image_slider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                prepareDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        cv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_tnc.isChecked()) {
                    savePreferences();
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                } else {
                    tv_tnc.setTextColor(Color.RED);
                }
            }
        });
    }

    private void initView() {
        vp_image_slider = findViewById(R.id.vp_image_slider);
        vp_image_slider.setAdapter(new StartInfoAdapter(getApplicationContext()));

        dots_container = findViewById(R.id.dots_container);

        cv_login = findViewById(R.id.cv_login);

        cb_tnc = findViewById(R.id.cb_tnc);
        tv_tnc = findViewById(R.id.tv_tnc);
        tv_tnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tnc_dialog.show();
            }
        });
        setTNCDialog();

        tv_login = findViewById(R.id.tv_login);
        tv_login.setLetterSpacing((float) 0.1);
        prepareDots(0);
    }

    private void setTNCDialog() {
        tnc_dialog = new AlertDialog.Builder(this)
            .setTitle(R.string.tnc)
            .setMessage(R.string.tnc_content)
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    tnc_dialog.dismiss();
                }
            })
            .create();
    }

    private void prepareDots(int currentSlider) {
        if (dots_container.getChildCount() > 0) {
            dots_container.removeAllViews();
        }

        ImageView dots[] = new ImageView[3];

        for (int i = 0; i < 3; i++) {
            dots[i] = new ImageView(this);
            if (i == currentSlider) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.image_silder_active_dot));
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.image_slider_inactive_dots));
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(12, 0, 12, 0);
            dots_container.addView(dots[i], layoutParams);
        }
    }

    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences("tnc",
                MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.clear();

        // Edit and commit
        editor.putBoolean("agree", true);
        editor.apply();
    }
}
