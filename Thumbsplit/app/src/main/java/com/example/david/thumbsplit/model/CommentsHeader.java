package com.example.david.thumbsplit.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.david.thumbsplit.AddCommentRequest;
import com.example.david.thumbsplit.R;
import com.example.david.thumbsplit.VideoDetailActivity;

import org.json.JSONObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Emanuela on 10/13/2017.
 */

public class CommentsHeader extends RecyclerView.ViewHolder {

    public String video_detail_url,token;
    public VideosListModel videoItem;
    public CircleImageView userImage;
    public TextView userName,videoTitle,createDate,Views,txtTagUsers,txtDescription;
    public Button btnLike,btnDislike,btnShare;
    public ImageButton fullScreenBtn,btnDescription,btnSendComment;
    public LinearLayout linearDescription,taggLayout;
    public boolean isPressed = false;
    public List<UserModel> userList;
    public EditText writeComment;




    public CommentsHeader(View itemView) {
        super(itemView);
        userImage=(CircleImageView) itemView.findViewById(R.id.video_user_img);
        userName=(TextView)itemView.findViewById(R.id.video_user_name);
        videoTitle=(TextView)itemView.findViewById(R.id.txt_video_title);
        Views=(TextView) itemView.findViewById(R.id.txt_video_views);
        btnLike=(Button)itemView.findViewById(R.id.btn_like);
        btnDislike=(Button)itemView.findViewById(R.id.btn_dislike);
        btnShare=(Button)itemView.findViewById(R.id.btn_share);
        createDate=(TextView)itemView.findViewById(R.id.text_video_date);
        linearDescription=(LinearLayout)itemView.findViewById(R.id.linear_description);
        btnDescription=(ImageButton)itemView.findViewById(R.id.btn_expand);
        fullScreenBtn=(ImageButton)itemView.findViewById(R.id.btn_fullscreen_player);
        linearDescription.setVisibility(View.GONE);
        txtTagUsers=(TextView)itemView.findViewById(R.id.txt_tag_users);
        txtDescription=(TextView)itemView.findViewById(R.id.txt_descriptions);
        taggLayout=(LinearLayout)itemView.findViewById(R.id.tagg_layout);
        btnSendComment=(ImageButton)itemView.findViewById(R.id.btn_add_comment_send);
        writeComment=(EditText)itemView.findViewById(R.id.edit_write_comment);

        btnDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPressed=!isPressed;
                if(isPressed){
                    btnDescription.setBackgroundResource(R.drawable.btn_colapse);
                    linearDescription.setVisibility(View.VISIBLE);
                }else{
                    btnDescription.setBackgroundResource(R.drawable.btn_expand);
                    linearDescription.setVisibility(View.GONE);
                }
            }
        });


    }


}
