package com.exmaple.jarvis.chat.Listener;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import com.exmaple.jarvis.chat.Activity.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.raywenderlich.android.validatetor.ValidateTor;

public class EditTextListener implements TextWatcher {
    private TextInputLayout mView;
    private Context mContext;
    private String mType;
    private TextInputEditText mEditText;

    public EditTextListener(Context context, TextInputLayout view, String type) {
        mContext = context;
        mView = view;
        mType = type;
    }

    public EditTextListener(Context context, TextInputLayout view, String type, TextInputEditText editText) {
        mContext = context;
        mView = view;
        mType = type;
        mEditText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mType.equals("general")) {
            if (s.length() < 8)
                mView.setError(mContext.getString(R.string.min_char_length));
            else
                mView.setError(null);
        } else if (mType.equals("email")) {
            ValidateTor validateTor = null;
            if (validateTor == null)
                validateTor = new ValidateTor();


            if (validateTor.isEmail(mEditText.getText().toString())) {
                mView.setError(null);
            } else {
                mView.setError(mContext.getString(R.string.email_correct_format));
            }
        }
    }
}
