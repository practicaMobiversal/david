package com.example.david.thumbsplit;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;
import com.example.david.thumbsplit.model.UserModel;
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
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.david.thumbsplit.R.drawable.btn_colapse;
import static java.security.AccessController.getContext;

public class VideoDetailActivity extends AppCompatActivity {

    private SimpleExoPlayerView playerView;
    private int currentWindow,videoId=0;
    private long playbackPosition;
    boolean playWhenReady = false;
    SimpleExoPlayer player;
    private String video_detail_url,token;
    private VideosListModel videoItem;
    private CircleImageView userImage;
    private TextView userName,videoTitle,createDate,Views,txtTagUsers,txtDescription;
    private Button btnLike,btnDislike,btnLength;
    private ImageButton fullScreenBtn,btnDescription,btnBack;
    private LinearLayout linearDescription;
    private boolean isPressed = false;
   private List<UserModel> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        playerView = (SimpleExoPlayerView) findViewById(R.id.simpleExoPlayerView);
        userImage=(CircleImageView) findViewById(R.id.video_user_img);
        userName=(TextView)findViewById(R.id.video_user_name);
        videoTitle=(TextView)findViewById(R.id.txt_video_title);
        Views=(TextView) findViewById(R.id.txt_video_views);
        btnLike=(Button)findViewById(R.id.btn_like);
        btnDislike=(Button)findViewById(R.id.btn_dislike);
        createDate=(TextView)findViewById(R.id.text_video_date);
        linearDescription=(LinearLayout)findViewById(R.id.linear_description);
        btnDescription=(ImageButton)findViewById(R.id.btn_expand);
        fullScreenBtn=(ImageButton)findViewById(R.id.btn_fullscreen_player);
        playerView.setControllerShowTimeoutMs(2500);
        linearDescription.setVisibility(View.GONE);
        txtTagUsers=(TextView)findViewById(R.id.txt_tag_users);
        txtDescription=(TextView)findViewById(R.id.txt_descriptions);
        btnBack=(ImageButton)findViewById(R.id._btn_detail_back);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        videoId=bundle.getInt("id");
        token=bundle.getString("token");

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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });





    }

    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT > 23)
        getVideo();



    }

    private void getVideo(){

        GetVideoDetail getVideoDetail=new GetVideoDetail(videoId, token, new VideoListListener() {
            @Override
            public void recivedVideoItem(VideosListModel videosListModel) {
                videoItem=videosListModel;
                video_detail_url=videoItem.getVideoUrl();
                initializePlayer();


                Picasso.with(VideoDetailActivity.this).load(videoItem.getVideoOwner().getProfileImg()).placeholder(R.drawable.ic_username)
                        .into(userImage);
                userName.setText(videoItem.getVideoOwner().getUsername());
                videoTitle.setText(videoItem.getVideoTitle());
                Views.setText(Integer.toString(videoItem.getViews())+" views");
                btnLike.setText(Integer.toString(videoItem.getLikeCount())+" Likes");
                btnDislike.setText(Integer.toString(videoItem.getDislikeCount())+" Dislikes");
                txtDescription.setText(videoItem.getDescription());
                userList=videoItem.getTaggedUsers();
                if(userList!=null){
                    txtTagUsers.setVisibility(View.VISIBLE);
                    for(int i=0;i<userList.size();i++){
                    txtTagUsers.setText(txtTagUsers+userList.get(i).getUsername());

                }}else{
                txtTagUsers.setVisibility(View.GONE);
                }

                java.util.Date dateObj = new java.util.Date(videoItem.getCreateDate());
                SimpleDateFormat date = new SimpleDateFormat("dd MMM yyyy");
                StringBuilder stringDate = new StringBuilder( date.format( dateObj ) );
                createDate.setText(stringDate);

                playbackPosition = player.getCurrentPosition();
                fullScreenBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent fullscreen=new Intent(VideoDetailActivity.this,FullscreenActivity.class);
                        Bundle bund=new Bundle();
                        bund.putString("video_url",videoItem.getVideoUrl());
                        bund.putLong("playback",player.getContentPosition());
                        fullscreen.putExtras(bund);
                        startActivity(fullscreen);
                    }
                });
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
