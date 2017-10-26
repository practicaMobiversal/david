package com.example.david.thumbsplit.model;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.david.thumbsplit.R;



public class BottomToolbarModel extends FrameLayout implements View.OnClickListener {


private ImageView mNewsfeed,myAccount,addVideo;

    public BottomToolbarModel(@NonNull Context context) {
        super(context);
        init(context);
    }
    public BottomToolbarModel(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottomToolbarModel(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.bottom_toolbar,this, true);

        addVideo = (ImageView) v.findViewById(R.id.iv_add_video);
        mNewsfeed = (ImageView) v.findViewById(R.id.iv_newsfeed);
        myAccount = (ImageView) v.findViewById(R.id.iv_account);
        mNewsfeed.setSelected(true);

        addVideo.setOnClickListener(this);
        myAccount.setOnClickListener(this);
        mNewsfeed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
        case R.id.iv_add_video:


        break;
        case R.id.iv_newsfeed:
            mNewsfeed.setSelected(true);
            myAccount.setSelected(false);

        break;
        case R.id.iv_account:
            myAccount.setSelected(true);
            mNewsfeed.setSelected(false);

        break;


    }
}}
