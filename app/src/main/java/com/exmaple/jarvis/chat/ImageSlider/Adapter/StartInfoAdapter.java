package com.exmaple.jarvis.chat.ImageSlider.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.exmaple.jarvis.chat.Activity.R;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class StartInfoAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private final ArrayList mImageList = new ArrayList<>(Arrays.asList(R.drawable.start_info1, R.drawable.start_info2, R.drawable.start_info3));
    private final ArrayList mContentList = new ArrayList<>(Arrays.asList(R.string.start_info1, R.string.start_info2, R.string.start_info3));

    public StartInfoAdapter(Context context) {
        mContext = context;

    }

    @Override
    public int getCount() {
        return mImageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = mInflater.inflate(R.layout.image_slider_layout, container, false);
        ImageView img_slider = v.findViewById(R.id.img_slider);
        TextView tv_content = v.findViewById(R.id.tv_content);

        Glide.with(mContext)
            .load(mImageList.get(position))
            .apply(new RequestOptions()
                .placeholder(R.drawable.placeholder)
                .fitCenter())
            .into(img_slider);

        tv_content.setText(
            mContext.getResources()
            .getString(Integer.parseInt(mContentList.get(position).toString())
        ));

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
