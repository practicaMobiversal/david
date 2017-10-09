package com.example.david.thumbsplit.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.david.thumbsplit.R;


public class FavoritesFragment extends Fragment {
    private View mMainView;

    public FavoritesFragment() {
        // Required empty public constructor
    }
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState){
    mMainView = inflater.inflate(R.layout.fragment_favorites, container, false);

    return mMainView;

}
}
