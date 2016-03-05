package com.test.android.a500px.picx500.listeners;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Alex on 05.03.2016.
 */
public abstract class ScrollPaginationListener extends RecyclerView.OnScrollListener{

    private static final int DEFAULT_PAGE_ITEMS_AMOUNT = 20;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoading;
    private int totalPagesAmount = Integer.MAX_VALUE;
    private int pageItemsAmount = DEFAULT_PAGE_ITEMS_AMOUNT;/*default value*/

    public ScrollPaginationListener(LinearLayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
    }

    public int getPageItemsAmount() {
        return pageItemsAmount;
    }

    public void setPageItemsAmount(int pageItemsAmount) {
        this.pageItemsAmount = pageItemsAmount;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void loadingStarted(){
        isLoading = true;
    }

    public void loadingStopped(){
        isLoading = false;
    }

    public int getTotalPagesAmount() {
        return totalPagesAmount;
    }

    public void setTotalPagesAmount(int totalPagesAmount) {
        this.totalPagesAmount = totalPagesAmount;
    }

    public int getNextLoadPageNumber() {
        return (int) Math.floor(mLayoutManager.getItemCount()/pageItemsAmount)+1;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView,
                                     int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = mLayoutManager.getChildCount();
        int totalItemCount = mLayoutManager.getItemCount();
        int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

        if (mLayoutManager.getItemCount()>0&&!isLoading && getNextLoadPageNumber()<totalPagesAmount) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= pageItemsAmount) {
                loadingStarted();
                loadNextPage(getNextLoadPageNumber(), pageItemsAmount);
            }
        }
    }

    public abstract void loadNextPage(int nextPageNumber, int amountOfItemsInPage);
}
