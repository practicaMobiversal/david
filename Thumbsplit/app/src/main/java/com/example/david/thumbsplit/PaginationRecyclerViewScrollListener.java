package com.example.david.thumbsplit;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by David on 10/10/2017.
 */

public abstract class PaginationRecyclerViewScrollListener extends RecyclerView.OnScrollListener{

    private LinearLayoutManager layoutManager;
    private int mOffset; // The position offset before the adapter reaches the end.

    public PaginationRecyclerViewScrollListener(LinearLayoutManager layoutManager, int mOffset) {
        this.layoutManager = layoutManager;
        this.mOffset = mOffset;
    }

    public PaginationRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        this.mOffset = 2;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0) {
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!isLoading() && !isLastPage()) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - mOffset) {
                    loadMoreItems();
                }
            }
        }
    }

    protected abstract void loadMoreItems();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();

}
