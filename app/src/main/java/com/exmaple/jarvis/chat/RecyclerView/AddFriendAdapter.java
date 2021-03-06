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
import com.exmaple.jarvis.chat.Model.User;
import com.exmaple.jarvis.chat.RecyclerView.ViewHolder.AddFriendViewHolder;
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

public class AddFriendAdapter extends RecyclerView.Adapter<AddFriendViewHolder> {
    private Context mContext;
    private List<User> mData;

    public AddFriendAdapter(Context context, List<User> userList) {
        mContext = context;
        mData = userList;
    }

    @NonNull
    @Override
    public AddFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(mContext)
                .inflate(R.layout.add_friend_item, parent, false);

        AddFriendViewHolder holder = new AddFriendViewHolder(view);

        holder.tv_username = (TextView) view.findViewById(R.id.tv_username);
        holder.img_avatar = (ImageView) view.findViewById(R.id.img_avatar);

        return holder;
    }

    @Override
    public void onBindViewHolder(AddFriendViewHolder holder, int position) {
        final User currentUser = (User) mData.get(position);

        holder.tv_username.setText(currentUser.getDisplayName());

        Glide.with(mContext)
                .load(currentUser.getAvatar())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .fitCenter())
                .into(holder.img_avatar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ChatActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("user2_username", currentUser.getUsername());
                i.putExtra("user2_displayname", currentUser.getDisplayName());
                i.putExtra("user2_avatar", currentUser.getAvatar());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
