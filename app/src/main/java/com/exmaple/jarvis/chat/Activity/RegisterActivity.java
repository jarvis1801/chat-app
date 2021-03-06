package com.exmaple.jarvis.chat.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.exmaple.jarvis.chat.Constant;
import com.exmaple.jarvis.chat.CustomImageView;
import com.exmaple.jarvis.chat.Dialog.IntentDialog;
import com.exmaple.jarvis.chat.Listener.EditTextListener;
import com.exmaple.jarvis.chat.Presenter.RegisterPresenter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.raywenderlich.android.validatetor.ValidateTor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterActivity extends AppCompatActivity implements RegisterView {
    private ImageView img_back;
    private CustomImageView img_avatar;
    private TextView tv_login_reminder;
    private CardView cv_login;

    private TextInputLayout til_username, til_password, til_display_name, til_email;
    private TextInputEditText et_username, et_password, et_display_name, et_email;
    private ValidateTor validateTor = new ValidateTor();

    private File avatarFile;

    private IntentDialog dialog = new IntentDialog();

    private Map<String, String> requestBodyData;

    private RegisterPresenter registerPresenter;

    private boolean pickedImage = false;

    private AlertDialog registerErrDialog;

    private android.app.AlertDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setViewElement();
        setViewElementEvent();
        setLoadingDialog();

        setPresenter();
    }

    private void setPresenter() {
        registerPresenter = new RegisterPresenter(this);
    }

    // image result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        dialog.dismiss();
        switch (requestCode){
            case 0:
                if (resultCode == RESULT_OK && imageReturnedIntent.getExtras().get("data") != null){
                    Log.e("test", imageReturnedIntent.getExtras().toString());
                    Bitmap selectedImage = (Bitmap) imageReturnedIntent.getExtras().get("data");

                    img_avatar.setImageBitmap(selectedImage);
                    pickedImage = true;
                }
            case 1:
                if (resultCode == RESULT_OK && imageReturnedIntent.getData() != null) {
                    final Uri imageUri = imageReturnedIntent.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(imageUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                    img_avatar.setImageBitmap(selectedImage);
                    pickedImage = true;
                }
        }
    }

    private void showDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        dialog.show(fragmentManager, "dialog");
    }

    private void setViewElement() {
        img_back = findViewById(R.id.img_back);
        img_avatar = findViewById(R.id.img_avatar);

        tv_login_reminder = findViewById(R.id.tv_login_reminder);

        cv_login = findViewById(R.id.cv_login);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_display_name = findViewById(R.id.et_display_name);
        et_email = findViewById(R.id.et_email);
        til_username = findViewById(R.id.til_username);
        til_password = findViewById(R.id.til_password);
        til_display_name = findViewById(R.id.til_display_name);
        til_email = findViewById(R.id.til_email);
    }

    private void setViewElementEvent() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_login_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        // EditText checking
        et_username.addTextChangedListener(new EditTextListener(getApplicationContext(), til_username, "general"));

        et_password.addTextChangedListener(new EditTextListener(getApplicationContext(), til_password, "general"));

        et_display_name.addTextChangedListener(new EditTextListener(getApplicationContext(), til_display_name, "general"));

        et_email.addTextChangedListener(new EditTextListener(getApplicationContext(), til_email, "email", et_email));

        cv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkField()) {
                    requestBodyData = new HashMap<String, String>();
                    requestBodyData.put("username", et_username.getText().toString());
                    requestBodyData.put("password", et_password.getText().toString());
                    requestBodyData.put("email", et_email.getText().toString());
                    requestBodyData.put("displayName", et_display_name.getText().toString());
                    Map<String, RequestBody> requestBody = Constant.generateRequestBody(requestBodyData);

                    MultipartBody.Part avatar;
                    if (pickedImage) {
                        avatarFile = Constant.persistImage(img_avatar, getApplicationContext());
                        RequestBody requestFile =
                                RequestBody.create(MediaType.parse("multipart/form-data"), avatarFile);

                        avatar = MultipartBody.Part.createFormData("avatar", avatarFile.getName(), requestFile);
                    } else {
                        avatar = null;
                    }
                    showLoadingDialog();
                    register(requestBody, avatar);
                }
            }
        });
    }

    @Override
    public void register(Map<String, RequestBody> requestBody, MultipartBody.Part avatar) {
        registerPresenter.postUser(requestBody, avatar);
    }

    @Override
    public void showUserExistDialog() {
        registerErrDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.register_failed))
                .setMessage(getString(R.string.username_is_already_exisit))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        registerErrDialog.dismiss();
                    }
                })
                .create();
        registerErrDialog.show();
    }

    public void showDatabaseErrDialog() {
        registerErrDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.connect_db_failed))
                .setMessage(getString(R.string.connect_db_failed_content))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        registerErrDialog.dismiss();
                        registerErrDialog = null;
                    }
                })
                .create();
        registerErrDialog.show();
    }

    private boolean checkField() {
        boolean isValid = true;
        if (et_username.length() < 8 || et_password.length() < 8 || et_email.length() < 8 || et_display_name.length() < 1
                && til_username.getError() != null || til_password.getError() != null || til_email.getError() != null || til_display_name.getError() != null) {
            isValid = false;
        }
        return isValid;
    }

    private void setLoadingDialog() {
        loadingDialog = new SpotsDialog.Builder()
                .setContext(RegisterActivity.this)
                .setMessage(getString(R.string.loading))
                .setCancelable(false)
                .build();
    }

    private void showLoadingDialog() {
        loadingDialog.show();
    }

    public void dismissLoadingDialog() {
        loadingDialog.dismiss();
    }

    public void registerSuccess() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
