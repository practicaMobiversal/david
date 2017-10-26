package com.example.david.thumbsplit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.VolumeShaper;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.StringRequest;
import com.example.david.thumbsplit.Adapters.CommentsAdapter;
import com.example.david.thumbsplit.model.AddNewCommentListener;
import com.example.david.thumbsplit.model.CommentsListListener;
import com.example.david.thumbsplit.model.CommentsModelList;
import com.example.david.thumbsplit.model.DeleteCommentListener;
import com.example.david.thumbsplit.model.MyListener;
import com.example.david.thumbsplit.model.OnCommentSend;
import com.example.david.thumbsplit.model.SharedVideoListener;
import com.example.david.thumbsplit.model.VideoListListener;
import com.example.david.thumbsplit.model.VideosListModel;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONObject;

import java.util.List;

public class VideoDetailActivity extends AppCompatActivity implements OnCommentSend, DeleteCommentListener,SharedVideoListener {

    public int page_size=3;
    private SimpleExoPlayerView playerView;
    private int currentWindow,videoId=0;
    private long playbackPosition;
    boolean playWhenReady = false;
    SimpleExoPlayer player;
    private String video_detail_url,token;
    private VideosListModel videoItem;
    private RecyclerView mCommentsRecylcer;
    private CommentsAdapter commentAdapter;
    private ImageButton fullScreenBtn,btnDescription,btnBack;
    private LinearLayout linearDescription;
    FrameLayout fullScreanFrame;
    private LinearLayoutManager layoutManager;
    JSONObject lastComment,firstLastComment;
    private PaginationRecyclerViewScrollListener mEndlessScrollListener;
    private boolean isLoading = false,isFullScreen=false,isLastPage = false;
    List<CommentsModelList> comments;
    private AlertDialog.Builder builder;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        playerView = (SimpleExoPlayerView) findViewById(R.id.simpleExoPlayerView);
        linearDescription=(LinearLayout)findViewById(R.id.linear_description);
        btnDescription=(ImageButton)findViewById(R.id.btn_expand);
        fullScreenBtn=(ImageButton)findViewById(R.id.btn_fullscreen_player);
        playerView.setControllerShowTimeoutMs(2500);
        btnBack=(ImageButton)findViewById(R.id._btn_detail_back);
        mCommentsRecylcer=(RecyclerView)findViewById(R.id.comment_recycler);
        mCommentsRecylcer.hasFixedSize();
        mCommentsRecylcer.setLayoutManager(layoutManager=new LinearLayoutManager(VideoDetailActivity.this));
        fullScreanFrame=(FrameLayout)findViewById(R.id.exo_fullscreen_button);
        builder=new AlertDialog.Builder(VideoDetailActivity.this);
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("video_auth");
        videoId=bundle.getInt("id");
        token=bundle.getString("token");
        initVideoPlayerSize();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fullScreenBtn.setVisibility(View.VISIBLE);
        fullScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent fullscreen=new Intent(VideoDetailActivity.this,FullscreenActivity.class);
//                Bundle bund=new Bundle();
//                bund.putString("video_url",videoItem.getVideoUrl());
//                bund.putLong("playback",player.getContentPosition());
//                fullscreen.putExtras(bund);
//                startActivityForResult(fullscreen,200);
                if(isFullScreen==false){
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
                playerView.setLayoutParams(params);
                isFullScreen=true;
                mCommentsRecylcer.setVisibility(View.GONE);
                fullScreenBtn.setBackgroundResource(R.drawable.btn_exit_fullscreen);
                }
                else if (isFullScreen==true){
                    int orie=getResources().getConfiguration().orientation;
                    if(orie==Configuration.ORIENTATION_LANDSCAPE )
                    {initVideoPlayerLandscapeSize();}
                    else
                    {
                        initVideoPlayerSize();
                    }
                    fullScreenBtn.setBackgroundResource(R.drawable.btn_fullscreen);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    mCommentsRecylcer.setVisibility(View.VISIBLE);
                    isFullScreen=false;


                }
            }
        });
    }
    private void initVideoPlayerSize() {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int videoHeight = screenWidth / 16 * 9;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
        params.height = videoHeight;
        params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        playerView.setLayoutParams(params);
    }
    private void initVideoPlayerLandscapeSize() {
        int screenWidth = getResources().getDisplayMetrics().heightPixels;
        int videoHeight = screenWidth / 16 * 9;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
        params.height = videoHeight;
        params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        playerView.setLayoutParams(params);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            // handle change here
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
            params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
            playerView.setLayoutParams(params);
            isFullScreen=true;
            mCommentsRecylcer.setVisibility(View.GONE);
        }
    }



    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT > 23) {
            getVideo();
        }
    }

    private void loadVideosFromApi(final CommentsAdapter commentAdapter) {
        isLoading = true;
        /// comment request
        final GetCommentRequest getCommentRequest=new GetCommentRequest(videoId, token, lastComment, page_size, new CommentsListListener() {
            @Override
            public void recivedCommentsList(List<CommentsModelList> commentsModelLists, JSONObject object) {

                isLastPage = commentsModelLists.size() < page_size;
                comments.addAll(commentsModelLists);
                lastComment = object;
                commentAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        });
        StringRequest arg=getCommentRequest.stringRequest;
        MySingleton.getInstance(VideoDetailActivity.this).addToRequestque(arg);

    }
    //send comment request
    @Override
    public void onCommentSend(String text){
        AddCommentRequest addCommentRequest=new AddCommentRequest(videoId, token, text, new AddNewCommentListener() {
            @Override
            public void addNewComment(CommentsModelList commentsModelList) {
                comments.add(0,commentsModelList);
                commentAdapter.setOnCommentSend(VideoDetailActivity.this);
                commentAdapter.notifyDataSetChanged();

            }
        }){};
        StringRequest arg1=addCommentRequest.stringRequest;
        MySingleton.getInstance(VideoDetailActivity.this).addToRequestque(arg1);
    }

// delete comment rquest
    @Override
    public void deleteComment(int deleteId, final int commentPosition) {
        DeleteCommentRequest deleteCommentRequest=new DeleteCommentRequest(token,deleteId, new MyListener() {
            @Override
            public void recivedCodeFromServer(int code) {
                if(code==200){
                    comments.remove(commentPosition-1);
                //  mCommentsRecylcer.removeViewAt(commentPosition);
                    commentAdapter.notifyItemRemoved(commentPosition);
                    commentAdapter.notifyItemRangeChanged(commentPosition,comments.size());
                }
            }
        });
        StringRequest arg1=deleteCommentRequest.stringRequest;
        MySingleton.getInstance(VideoDetailActivity.this).addToRequestque(arg1);
    }

    @Override
    public void sharedVideo(VideosListModel videosListModel) {
Intent share=new Intent(VideoDetailActivity.this,ShareVideoActivity.class);
        share.putExtra("share_url",videosListModel.getVideoUrl());
        share.putExtra("title",videosListModel.getVideoTitle());
        startActivityForResult(share,300);
    }
    public void getVideo(){

        GetVideoDetail getVideoDetail=new GetVideoDetail(videoId, token, new VideoListListener() {
            @Override
            public void recivedVideoItem(VideosListModel videosListModel,JSONObject object) {
                videoItem=videosListModel;
                lastComment=object;
                firstLastComment=object;
                video_detail_url=videoItem.getVideoUrl();
                comments =videosListModel.getCommentsModelLists();
                commentAdapter=new CommentsAdapter(VideoDetailActivity.this,videosListModel,comments);

                commentAdapter.setItems(comments);
                commentAdapter.setHeader(videosListModel);
                mCommentsRecylcer.setAdapter(commentAdapter);
                commentAdapter.setOnCommentSend(VideoDetailActivity.this);
                commentAdapter.setOnMenuClick(VideoDetailActivity.this);
                commentAdapter.setSharedVideo(VideoDetailActivity.this);
                commentAdapter.notifyDataSetChanged();
                initializePlayer();
            }
        });
        StringRequest arg=getVideoDetail.stringRequest;
        MySingleton.getInstance(VideoDetailActivity.this).addToRequestque(arg);
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
         if ((Util.SDK_INT <= 23 || player == null)) {
           getVideo();
       }

        mEndlessScrollListener = new PaginationRecyclerViewScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                loadVideosFromApi(commentAdapter);
            }
            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        };
        mCommentsRecylcer.addOnScrollListener(mEndlessScrollListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200){
            if(resultCode==RESULT_OK){

                playbackPosition=data.getLongExtra("playback",0);
                isLastPage=false;
                lastComment=firstLastComment;
            }
            }
           else if(requestCode==300)
        { if(resultCode==RESULT_OK)
                {
                    builder.setTitle("Congratulations!");
                    builder.setMessage("You succesfullyu shared this video");
                    displayAlert(300);
                }
                else if(resultCode!=RESULT_OK){
                    builder.setTitle("Something went wrong...");
                    builder.setMessage("Please try again");
                    displayAlert(500);
                }}
    }
    private void displayAlert(final int code) {
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(code==300){


                }
                else if(code>=500){

                }
            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);

                Uri uri = Uri.parse(video_detail_url);
                MediaSource mediaSource = buildMediaSource(uri);
                player.prepare(mediaSource, true, false);
    }


    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }


}
