package com.example.david.thumbsplit.Adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.david.thumbsplit.R;
import com.example.david.thumbsplit.model.CommentsFooter;
import com.example.david.thumbsplit.model.CommentsHeader;
import com.example.david.thumbsplit.model.CommentsModelList;
import com.example.david.thumbsplit.model.CommentsSection;
import com.example.david.thumbsplit.model.DeleteCommentListener;
import com.example.david.thumbsplit.model.OnCommentSend;
import com.example.david.thumbsplit.model.SharedVideoListener;
import com.example.david.thumbsplit.model.VideosListModel;
import com.karumi.headerrecyclerview.HeaderRecyclerViewAdapter;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Emanuela on 10/13/2017.
 */

public class CommentsAdapter extends HeaderRecyclerViewAdapter<RecyclerView.ViewHolder,VideosListModel,CommentsModelList,CommentsFooter> {

    private VideosListModel videoItem;
    private Context context;
    private List<CommentsModelList> comments;
    private CommentsModelList singleComment;
    OnCommentSend commentSend;
    private String comm;
    private CommentsHeader holder1;
    DeleteCommentListener commentIdListener;
    private SharedVideoListener shareVideo;


    public CommentsAdapter(Context context, VideosListModel modelVideo,List<CommentsModelList>comments){
        this.context = context;
        this.videoItem=modelVideo;
        this.comments=comments;
    }



    @Override
    protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = getLayoutInflater(parent);
        View headerView = inflater.inflate(R.layout.video_details_info, parent, false);

        return new CommentsHeader(headerView);
    }
    @Override
    protected void onBindHeaderViewHolder(final RecyclerView.ViewHolder holder, int position) {
        holder1=(CommentsHeader)holder;
        String tagged="";

        Picasso.with(context).load(videoItem.getVideoOwner().getProfileImg()).placeholder(R.drawable.ic_username)
                .into(holder1.userImage);
        holder1.userName.setText(videoItem.getVideoOwner().getUsername());
        holder1.videoTitle.setText(videoItem.getVideoTitle());
        holder1.Views.setText(Integer.toString(videoItem.getViews())+" views");
        holder1.btnLike.setText(Integer.toString(videoItem.getLikeCount())+" Likes");
        holder1.btnDislike.setText(Integer.toString(videoItem.getDislikeCount())+" Dislikes");
        holder1.txtDescription.setText(videoItem.getDescription());
        holder1. userList=videoItem.getTaggedUsers();
        if(holder1.userList.size()>0){
            holder1.taggLayout.setVisibility(View.VISIBLE);
            holder1.txtTagUsers.setText("");
            tagged=String.valueOf(holder1.userList.get(0).getUsername());
            for(int i=1;i<holder1.userList.size();i++){
            tagged=tagged+", "+String.valueOf(holder1.userList.get(i).getUsername());

            }
            holder1.txtTagUsers.setText(tagged);}else{
            holder1.taggLayout.setVisibility(View.GONE);
        }

        java.util.Date dateObj = new java.util.Date(videoItem.getCreateDate());
        SimpleDateFormat date = new SimpleDateFormat("dd MMM yyyy");
        StringBuilder stringDate = new StringBuilder( date.format( dateObj ) );
        holder1.createDate.setText(stringDate);


        holder1.btnSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comm= String.valueOf(holder1.writeComment.getText());
                commentSend.onCommentSend(comm);
                holder1.writeComment.setText(null);
                hideKeyboard(v);
            }
        });

holder1.btnShare.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        shareVideo.sharedVideo(videoItem);
    }
});



    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = getLayoutInflater(parent);
        View itemView=inflater.inflate(R.layout.single_comment,parent,false);
        return new CommentsSection(itemView);
    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int deleteCommentId,newPosition;
        final CommentsSection holder2=(CommentsSection)holder;
        singleComment=getItem(position);
        Picasso.with(context).load(singleComment.getUser().getProfileImg()).placeholder(R.drawable.ic_username)
                .into(holder2.profileImage);
        holder2.comment.setText(singleComment.getText());
        holder2.username.setText(singleComment.getUser().getUsername());
        holder2.likes.setText(Integer.toString(singleComment.getLike()));
        holder2.dislakes.setText(Integer.toString(singleComment.getDislike()));

        java.util.Date dateObj = new java.util.Date(singleComment.getCreatedAt());
        SimpleDateFormat date = new SimpleDateFormat("dd MMM yyyy");
        StringBuilder stringDate = new StringBuilder( date.format( dateObj ) );
        holder2.createDate.setText(stringDate);

        deleteCommentId=singleComment.getCommentId();

        holder2.commentMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                PopupMenu popupMenu=new PopupMenu(context,((CommentsSection) holder2).commentMenu);
                popupMenu.getMenuInflater().inflate(R.menu.comment_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        CharSequence titleDelete="Delete Comment";
                        if(item.getTitle().equals(titleDelete))
                            commentIdListener.deleteComment(deleteCommentId,position);
                        return  true;
                    }
                });
             popupMenu.show();
            }
        });

    }

    private LayoutInflater getLayoutInflater(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext());
    }
public void setSharedVideo(SharedVideoListener sharedVideoListener){this.shareVideo=sharedVideoListener;}
    public void setOnCommentSend(OnCommentSend onCommentSend){
        this.commentSend=onCommentSend;
    }
    public void setOnMenuClick(DeleteCommentListener menuClick){
        this.commentIdListener=menuClick;
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
