package com.gitfeaturelisting.component;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kunal.Mahajan on 7/23/2018.
 */

public abstract class DynamicLoadingAdapter extends RecyclerView.Adapter<ViewHolder> {

    private static final int ITEM = 3;
    private static final int LOADING = 4;
    private static final int NO_DATA = 5;

    protected List records;
    protected Context context;
    private LoadingObj loadingObj = new LoadingObj();
    private NoDataAvailableObj noDataAvailableObj = new NoDataAvailableObj();

    public DynamicLoadingAdapter(Context context) {
        this.context = context;
        records = new ArrayList<>();
        records.add(loadingObj);
    }

    private int getIntToDP(int px) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics()));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                viewHolder = new DynamicLoadingAdapter.LoadingVH(getProgressBar());
                break;
            case NO_DATA:
                viewHolder = new ViewHolder(getNoDataTextView()) {
                };
        }
        return viewHolder;
    }

    protected abstract String getEmptyText();

    @NonNull
    protected abstract ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater);

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM)
            setValuesOnBind(holder, position);
    }

    protected abstract void setValuesOnBind(ViewHolder holder, int position);

    @Override
    public int getItemViewType(int position) {
        Object o = records.get(position);
        if (o instanceof LoadingObj)
            return LOADING;

        if (o instanceof NoDataAvailableObj)
            return NO_DATA;

        return ITEM;
    }

    void setLoadingFinished() {
        records.remove(records.size() - 1);
        if (records.size() == 0) records.add(new NoDataAvailableObj());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    int getItemLoadedCount() {
        int l = records.size();
        if (records.get(records.size() - 1).equals(loadingObj))
            l--;

        if (l > 0 && records.get(0).equals(noDataAvailableObj))
            l--;

        return l;
    }

    void add(List list) {
        for (int i = 0; i < list.size(); i++) {
            records.add(records.size() - 1, list.get(i));
        }

        notifyDataSetChanged();
    }

    private ProgressBar getProgressBar() {
        ProgressBar progressBar = new ProgressBar(context);
        int h = getIntToDP(30);
        int w = ViewGroup.LayoutParams.MATCH_PARENT;
        LinearLayout.LayoutParams progressParams = new LinearLayout.LayoutParams(w, h);
        int margin = getIntToDP(5);
        progressParams.setMargins(margin, margin, margin, margin);
        progressParams.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(progressParams);
        return progressBar;
    }

    public TextView getNoDataTextView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setText(getEmptyText());
        textView.setTypeface(null, Typeface.ITALIC);
        params.setMargins(0, getIntToDP(7), 0, getIntToDP(7));
        textView.setLayoutParams(params);
        return textView;
    }

    private static class LoadingObj {
    }

    private class LoadingVH extends ViewHolder {
        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

    private class NoDataAvailableObj {
    }
}
