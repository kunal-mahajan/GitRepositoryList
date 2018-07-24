package com.gitfeaturelisting.component;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.List;

/**
 * Created by Kunal.Mahajan on 7/23/2018.
 */

public abstract class DynamicLoadingListHelper {

    private final DynamicLoadingAdapter adapter;
    private final Context context;
    private LinearLayout containerLayout;
    private RecyclerView rv;
    private int totalRecords = -1;
    private int pageCount = 0;

    public DynamicLoadingListHelper(Context context, LinearLayout containerLayout, DynamicLoadingAdapter adapter) {
        this.adapter = adapter;
        this.context = context;
        this.containerLayout = containerLayout;
        init();
    }

    public void init() {
        final LinearLayoutManager lm = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv = new RecyclerView(context);
        rv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        containerLayout.addView(rv);
        rv.setLayoutManager(lm);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastLoadCountReq = Integer.MIN_VALUE;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (lm.findLastVisibleItemPosition() == adapter.getItemCount() - 1 && newState == RecyclerView.SCROLL_STATE_IDLE && adapter.getItemLoadedCount() < totalRecords && adapter.getItemLoadedCount() > lastLoadCountReq) {
                    lastLoadCountReq = adapter.getItemLoadedCount();
                    loadNextPage();
                }
            }
        });

        rv.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();

            @Override
            public void onChildViewAttachedToWindow(View view) {

                boolean isScrolled = layoutManager.findViewByPosition(0) != layoutManager.findViewByPosition(layoutManager.findFirstCompletelyVisibleItemPosition());
                if (isScrolled)
                    return;

                if (!(view instanceof ProgressBar))
                    return;

                if (isRecyclerScrollable()) {
                    loadNextPage();
                }
            }

            public void onChildViewDetachedFromWindow(View view) {

            }
        });

        loadNextPage();
    }

    private void loadNextPage() {
        pageCount++;
        loadData(pageCount);
    }

    private boolean isRecyclerScrollable() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();
        RecyclerView.Adapter adapter = rv.getAdapter();

        if (layoutManager == null || adapter == null) return false;

        return layoutManager.findLastCompletelyVisibleItemPosition() < totalRecords;
    }

    protected abstract void loadData(int offset);

    public void dataLoaded(List list, int totalPage) {

        if (totalPage <= 0) {
            adapter.setLoadingFinished();
            return;
        }

        this.totalRecords = totalPage;
        adapter.add(list);
        if (adapter.getItemLoadedCount() >= totalPage)
            adapter.setLoadingFinished();
    }
}
