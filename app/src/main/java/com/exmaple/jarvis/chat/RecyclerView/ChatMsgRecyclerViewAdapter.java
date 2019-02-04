package com.exmaple.jarvis.chat.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.exmaple.jarvis.chat.Activity.R;
import com.exmaple.jarvis.chat.Model.ChatMessageListItem;
import com.exmaple.jarvis.chat.Model.Message;
import com.exmaple.jarvis.chat.RecyclerView.ViewHolder.ChatMsgViewHolder;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatMsgRecyclerViewAdapter extends RecyclerView.Adapter<ChatMsgViewHolder> {
    private Context mContext;
    private List<ChatMessageListItem> mData;

    public ChatMsgRecyclerViewAdapter(Context context, List<ChatMessageListItem> msgList) {
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
        final ChatMessageListItem currentMsg = (ChatMessageListItem) mData.get(position);

        holder.tv_username.setText(currentMsg.getOtherUser().getDisplayName());
        holder.tv_message.setText(currentMsg.getMessage());
        holder.tv_date_time.setText(getMsgDate(currentMsg.getPostedAt()));

            Glide.with(mContext)
                    .load(currentMsg.getOtherUser().getAvatar())
                    .into(holder.img_user_avatar);
    }

    private String getMsgDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.CHINESE);
        String today = sdf.format(new Date());

        String value = null;

        if (date.compareTo(today) < 1) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.CHINESE);
            try {
                value = dateFormat.format(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (date.compareTo(today) < 2) {
            value = mContext.getString(R.string.yesterday);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.CHINESE);
            try {
                value = dateFormat.format(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
