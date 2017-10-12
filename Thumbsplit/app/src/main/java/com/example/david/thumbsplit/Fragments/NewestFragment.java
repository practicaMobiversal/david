package com.example.david.thumbsplit.Fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.StringRequest;
import com.example.david.thumbsplit.GetVideoRequest;
import com.example.david.thumbsplit.HomeActivity;
import com.example.david.thumbsplit.MySingleton;
import com.example.david.thumbsplit.PaginationRecyclerViewScrollListener;
import com.example.david.thumbsplit.R;
import com.example.david.thumbsplit.VideoAdapter;
import com.example.david.thumbsplit.model.VideoListListener;
import com.example.david.thumbsplit.model.VideosListListener;
import com.example.david.thumbsplit.model.VideosListModel;
import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public class NewestFragment extends Fragment {
    private View mMainView;
    private RecyclerView mNewestrecycler;
   private String token=HomeActivity.jwt;
    private VideosListModel videosListItem;
    private List<VideosListModel> mList=new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private PaginationRecyclerViewScrollListener mEndlessScrollListener;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    VideoAdapter videoAdapter;
    JSONObject last_element;
    int page_size=3;
    int i;



    public NewestFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        mMainView = inflater.inflate(R.layout.fragment_newest, container, false);
        mNewestrecycler=(RecyclerView)mMainView.findViewById(R.id.newest_recycler);
        mNewestrecycler.hasFixedSize();
        mNewestrecycler.setLayoutManager(layoutManager=new LinearLayoutManager(getContext()));


        videoAdapter=new VideoAdapter(getContext(),mList);
        GetVideoRequest video_request=new GetVideoRequest(token,last_element, new VideosListListener() {

            @Override
            public void recivedVideosList(List<VideosListModel> videosListModel, JSONObject last_obj){

                    mList.addAll(videosListModel);
                    videoAdapter.notifyDataSetChanged();
                last_element=last_obj;
            }
        });

        StringRequest arg=video_request.stringRequest;
        MySingleton.getInstance(getContext()).addToRequestque(arg);



        mNewestrecycler.setAdapter(videoAdapter);

        mEndlessScrollListener = new PaginationRecyclerViewScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                loadVideosFromApi(videoAdapter);

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

        mNewestrecycler.addOnScrollListener(mEndlessScrollListener);


        return mMainView;
    }

    private void loadVideosFromApi(final VideoAdapter videoAdapter) {
        isLoading = true;
        GetVideoRequest video_request=new GetVideoRequest(token,last_element, new VideosListListener() {
            @Override
            public void recivedVideosList(List<VideosListModel> vList,JSONObject last_obj) {

                if(vList.size()==page_size) {
                    mList.addAll(vList);
                    last_element = last_obj;
                    videoAdapter.notifyDataSetChanged();
                    isLoading = false;
                }
                else isLastPage=true;


            }

        });

        StringRequest arg=video_request.stringRequest;
        MySingleton.getInstance(getContext()).addToRequestque(arg);

    }


    @Override
    public void onStart() {
        super.onStart();


    }
    }






