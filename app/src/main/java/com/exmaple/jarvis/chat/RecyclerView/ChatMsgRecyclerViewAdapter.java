package com.exmaple.jarvis.chat.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.exmaple.jarvis.chat.Activity.R;
import com.exmaple.jarvis.chat.Model.Message;
import com.exmaple.jarvis.chat.RecyclerView.ViewHolder.ChatMsgViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatMsgRecyclerViewAdapter extends RecyclerView.Adapter<ChatMsgViewHolder> {
    private Context mContext;
    private List<Message> mData;

    public ChatMsgRecyclerViewAdapter(Context context, List<Message> msgList) {
        mContext = context;
        mData = msgList;
    }

    @NonNull
    @Override
    public ChatMsgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(mContext)
                .inflate(R.layout.chat_list_item, parent, false);

        ChatMsgViewHolder holder = new ChatMsgViewHolder(view);

        holder.tv_username = (TextView) view.findViewById(R.id.tv_username);
        holder.tv_date_time = (TextView) view.findViewById(R.id.tv_date_time);
        holder.tv_message = (TextView) view.findViewById(R.id.tv_message);
        holder.img_user_avatar = (ImageView) view.findViewById(R.id.img_user_avatar);

        return holder;
    }

    @Override
    public void onBindViewHolder(ChatMsgViewHolder holder, int position) {
        final Message currentMsg = (Message) mData.get(position);

        holder.tv_username.setText("Test");
        holder.tv_message.setText(currentMsg.getMessage());
        holder.tv_date_time.setText(currentMsg.getPostedAt());

//        Glide.with(mContext)
//                .load(currentMsg.)
//                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
