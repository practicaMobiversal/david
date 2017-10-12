package com.example.david.thumbsplit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.ViewGroup;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david.thumbsplit.model.VideosListModel;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.os.Build.VERSION_CODES.O;
import static com.squareup.picasso.Picasso.with;
import static java.security.AccessController.getContext;


/**
 * Created by David on 9/27/2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private Context mContext;
    private List<VideosListModel>modelList;
    private int videoId;
    private String token;

    public List<VideosListModel> getModelList() {
        return modelList;
    }

    public VideoAdapter(Context context, List<VideosListModel> modelList){
        this.mContext=context;
        this.modelList=modelList;
    }


    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_video_layout,null);
        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(VideoAdapter.ViewHolder holder, int position) {
final VideosListModel modelVideo=modelList.get(position);

        with(mContext).load(modelVideo.getThumbnail()).error(R.drawable.login_logo_new)
                .placeholder(R.drawable.login_logo_new)
                .into(holder.videoImage);
        Picasso.with(mContext).load(modelVideo.getVideoOwner().getProfileImg()).error(R.drawable.login_logo)
                .placeholder(R.drawable.login_logo)
                .into(holder.userImage);

        holder.userName.setText(modelVideo.getVideoOwner().getUsername());
        holder.videoTitle.setText(modelVideo.getVideoTitle());
        holder.btnViews.setText(Integer.toString(modelVideo.getViews())+" views");
        holder.btnLike.setText(Integer.toString(modelVideo.getLikeCount())+" Likes");
        holder.btnDislike.setText(Integer.toString(modelVideo.getDislikeCount())+" Dislikes");
        holder.btnLength.setText(Integer.toString(modelVideo.getVideoLenght()));

//       date
        java.util.Date dateObj = new java.util.Date(modelVideo.getCreateDate());
        SimpleDateFormat date = new SimpleDateFormat("dd MMM yyyy");
        StringBuilder stringDate = new StringBuilder( date.format( dateObj ) );
        holder.createDate.setText(stringDate);

        videoId=modelVideo.getId();
        token=modelVideo.getVideoOwner().getToken();


        holder.videoImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,VideoDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("id",modelVideo.getId());
                bundle.putString("token",token);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

    }




    @Override
    public int getItemCount() {
        return (null!=modelList?modelList.size():0);
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected CircleImageView userImage;
        protected TextView userName, videoTitle, createDate;
        protected ImageView videoImage;
        protected Button btnViews, btnLike, btnDislike, btnLength;


        public ViewHolder(final View itemView) {
            super(itemView);
            userImage = (CircleImageView) itemView.findViewById(R.id.single_user_img);
            userName = (TextView) itemView.findViewById(R.id.text_fullname);
            videoTitle = (TextView) itemView.findViewById(R.id.text_video_title);
            videoImage = (ImageView) itemView.findViewById(R.id.image_video_list);
            createDate = (TextView) itemView.findViewById(R.id.text_date);
            btnViews = (Button) itemView.findViewById(R.id.btn_views);
            btnLike = (Button) itemView.findViewById(R.id.btn_like);
            btnDislike = (Button) itemView.findViewById(R.id.btn_dislike);
            btnLength = (Button) itemView.findViewById(R.id.btn_video_length);


            btnLike.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            btnDislike.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

}






