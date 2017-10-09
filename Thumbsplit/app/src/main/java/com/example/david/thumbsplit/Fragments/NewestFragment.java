package com.example.david.thumbsplit.Fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.StringRequest;
import com.example.david.thumbsplit.GetVideoRequest;
import com.example.david.thumbsplit.HomeActivity;
import com.example.david.thumbsplit.MySingleton;
import com.example.david.thumbsplit.R;
import com.example.david.thumbsplit.VideoAdapter;
import com.example.david.thumbsplit.model.VideoListListener;
import com.example.david.thumbsplit.model.VideosListModel;

import java.util.ArrayList;
import java.util.List;


public class NewestFragment extends Fragment {
    private View mMainView;
    private RecyclerView mNewestrecycler;
   private String token=HomeActivity.jwt;
    private VideosListModel videosListItem;
    private List<VideosListModel> mList=new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;


    public NewestFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        mMainView = inflater.inflate(R.layout.fragment_newest, container, false);
        mNewestrecycler=(RecyclerView)mMainView.findViewById(R.id.newest_recycler);
        mNewestrecycler.hasFixedSize();
        mNewestrecycler.setLayoutManager(new LinearLayoutManager(getContext()));


        final VideoAdapter videoAdapter=new VideoAdapter(getContext(),mList);
        GetVideoRequest video_request=new GetVideoRequest(token, new VideoListListener() {
            @Override
            public void recivedVideoItem(VideosListModel videosListModel) {
                videosListItem=videosListModel;
                mList.add(videosListModel);
                videoAdapter.notifyDataSetChanged();
            }
        });

        StringRequest arg=video_request.stringRequest;
        MySingleton.getInstance(getContext()).addToRequestque(arg);

        mNewestrecycler.setAdapter(videoAdapter);

        return mMainView;
    }




    @Override
    public void onStart() {
        super.onStart();


    }
    }






