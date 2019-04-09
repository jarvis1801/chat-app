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
import com.exmaple.jarvis.chat.Model.ChatRoomMessage;
import com.exmaple.jarvis.chat.RecyclerView.ViewHolder.ChatMsgViewHolder;
import com.exmaple.jarvis.chat.RecyclerView.ViewHolder.ChatRoomMsgViewHolder;
import com.github.thunder413.datetimeutils.DateTimeUnits;
import com.github.thunder413.datetimeutils.DateTimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatRoomMsgAdapter extends RecyclerView.Adapter<ChatRoomMsgViewHolder> {
    private Context mContext;
    private List<ChatRoomMessage> mData;
    private String me;

    private final static int SENDER = 1, RECEIVER = 2;

    public ChatRoomMsgAdapter(Context context, List<ChatRoomMessage> msgList, String username) {
        mContext = context;
        mData = msgList;
        me = username;
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return mData.get(position).getSender().equals(me) ? RECEIVER : SENDER;
    }

    @NonNull
    @Override
    public ChatRoomMsgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        ChatRoomMsgViewHolder holder = null;
        switch (viewType) {
            case 1:
                view = (View) LayoutInflater.from(mContext)
                        .inflate(R.layout.receiver_message_item, parent, false);

                holder = new ChatRoomMsgViewHolder(view);

                holder.tv_content = (TextView) view.findViewById(R.id.tv_content);
                holder.tv_date = (TextView) view.findViewById(R.id.tv_date);
                holder.tv_time = (TextView) view.findViewById(R.id.tv_time);
                return holder;
            case 2:
                view = (View) LayoutInflater.from(mContext)
                        .inflate(R.layout.sender_message_item, parent, false);

                holder = new ChatRoomMsgViewHolder(view);

                holder.tv_content = (TextView) view.findViewById(R.id.tv_content);
                holder.tv_date = (TextView) view.findViewById(R.id.tv_date);
                holder.tv_time = (TextView) view.findViewById(R.id.tv_time);
                return holder;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(ChatRoomMsgViewHolder holder, int position) {
        final ChatRoomMessage currentMsg = (ChatRoomMessage) mData.get(position);

        holder.tv_content.setText(currentMsg.getMessage());
        holder.tv_time.setText(getTime(currentMsg.getPostedAt()));
        holder.tv_date.setText(getDate(currentMsg.getPostedAt()));

    }

    private String getDate(String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.CHINESE);
        SimpleDateFormat sdf_date = new SimpleDateFormat("d/M/YY", Locale.CHINESE);
        Date date = null;
        String outputDateString = null;
        try {
            date = sdf.parse(datetime);
            outputDateString = sdf_date.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateString;
    }

    private String getTime(String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.CHINESE);
        SimpleDateFormat sdf_date = new SimpleDateFormat("HH:mm", Locale.CHINESE);
        Date date = null;
        String outputDateString = null;
        try {
            date = sdf.parse(datetime);
            outputDateString = sdf_date.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateString;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
