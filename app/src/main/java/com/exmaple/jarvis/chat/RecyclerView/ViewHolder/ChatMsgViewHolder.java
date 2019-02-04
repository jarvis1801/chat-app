package com.exmaple.jarvis.chat.RecyclerView.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ChatMsgViewHolder extends RecyclerView.ViewHolder {
    public ImageView img_user_avatar;
    public TextView tv_username, tv_date_time, tv_message;

    public ImageView img_thumbnail;

    public ChatMsgViewHolder(View itemView) {
        super(itemView);
    }
}
