package com.exmaple.jarvis.chat.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.exmaple.jarvis.chat.Activity.ChatActivity;
import com.exmaple.jarvis.chat.Activity.R;
import com.exmaple.jarvis.chat.Model.ChatMessageListItem;
import com.exmaple.jarvis.chat.Model.Message;
import com.exmaple.jarvis.chat.RecyclerView.ViewHolder.ChatMsgViewHolder;
import com.github.thunder413.datetimeutils.DateTimeUnits;
import com.github.thunder413.datetimeutils.DateTimeUtils;

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
    private String me;

    public ChatMsgRecyclerViewAdapter(Context context, List<ChatMessageListItem> msgList, String username) {
        mContext = context;
        mData = msgList;
        me = username;
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
        String msg = currentMsg.getSender().equals(me) ? mContext.getString(R.string.me) + " " + currentMsg.getMessage() : currentMsg.getMessage();
        holder.tv_message.setText(msg);
        holder.tv_date_time.setText(getMsgDate(currentMsg.getPostedAt()));

        Glide.with(mContext)
                .load(currentMsg.getOtherUser().getAvatar())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .fitCenter())
                .into(holder.img_user_avatar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ChatActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("group_id", currentMsg.getGroupId());
                i.putExtra("user2_username", currentMsg.getOtherUser().getUsername());
                i.putExtra("user2_displayname", currentMsg.getOtherUser().getDisplayName());
                i.putExtra("user2_avatar", currentMsg.getOtherUser().getAvatar());
                mContext.startActivity(i);
            }
        });
    }

    private String getMsgDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.CHINESE);
        String today = sdf.format(new Date());

        String value = null;

        Log.e("test", String.valueOf(date.compareTo(today)));
        int dayDiff = DateTimeUtils.getDateDiff(date, today, DateTimeUnits.DAYS);
        Log.e("test", String.valueOf(dayDiff));
        if (dayDiff >= -1) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.CHINESE);
            try {
                value = dateFormat.format(sdf.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (dayDiff >= -2) {
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
