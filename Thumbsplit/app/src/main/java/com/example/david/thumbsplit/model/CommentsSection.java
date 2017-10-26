package com.example.david.thumbsplit.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david.thumbsplit.R;

/**
 * Created by Emanuela on 10/13/2017.
 */

public class CommentsSection extends RecyclerView.ViewHolder {
    public TextView username;
    public TextView comment;
    public TextView likes;
    public TextView dislakes;
    public TextView createDate;
    public ImageView profileImage;
    public ImageView commentMenu;



    public CommentsSection(View itemView) {
        super(itemView);
        username=(TextView)itemView.findViewById(R.id.comment_item_username);
        comment=(TextView)itemView.findViewById(R.id.comment_item_message);
        likes=(TextView)itemView.findViewById(R.id.comment_like_comment);
        dislakes=(TextView)itemView.findViewById(R.id.comment_dislike_comment);
        createDate=(TextView)itemView.findViewById(R.id.comment_item_date);
        profileImage=(ImageView)itemView.findViewById(R.id.comment_item_profile_img);
        commentMenu=(ImageView)itemView.findViewById(R.id.comment_item_more);

    }
}
