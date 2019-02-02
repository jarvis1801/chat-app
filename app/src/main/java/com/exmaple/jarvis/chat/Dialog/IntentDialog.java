package com.exmaple.jarvis.chat.Dialog;import android.Manifest;import android.app.Dialog;import android.content.Intent;import android.content.pm.PackageManager;import android.os.Build;import android.os.Bundle;import android.provider.MediaStore;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.view.Window;import android.widget.ImageView;import android.widget.TextView;import com.exmaple.jarvis.chat.Activity.R;import org.jetbrains.annotations.NotNull;import androidx.core.app.ActivityCompat;import androidx.fragment.app.DialogFragment;import static androidx.core.content.PermissionChecker.checkSelfPermission;public class IntentDialog extends DialogFragment {    private ImageView img_camera, img_gallery;    private TextView tv_cancel;    @Override    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,                             Bundle savedInstanceState) {        View v = inflater.inflate(R.layout.dialog_intent, container, false);        initView(v);        return v;    }    @NotNull    @Override    public Dialog onCreateDialog(Bundle savedInstanceState) {        Dialog dialog = super.onCreateDialog(savedInstanceState);        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);        return dialog;    }    private void initView(View v) {        img_camera = v.findViewById(R.id.img_camera);        img_gallery = v.findViewById(R.id.img_gallery);        tv_cancel = v.findViewById(R.id.tv_cancel);        setViewOnClick();        try {            final Window window = getDialog().getWindow();            window.setBackgroundDrawableResource(R.color.transparent);        } catch (Exception e) {            e.printStackTrace();        }    }    private void setViewOnClick() {        img_camera.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);                getActivity().startActivityForResult(takePicture, 0);//zero can be replaced with any action code            }        });        img_gallery.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                if (isStoragePermissionGranted()) {                    Intent i = new Intent(Intent.ACTION_PICK);                    i.setType("image/*");                    getActivity().startActivityForResult(i, 1);                }            }        });        tv_cancel.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                dismiss();            }        });    }    private boolean isStoragePermissionGranted() {        if (Build.VERSION.SDK_INT >= 23) {            if (checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)                    == PackageManager.PERMISSION_GRANTED) {                return true;            } else {                ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE }, 2);                return false;            }        }        else {            return true;        }    }}