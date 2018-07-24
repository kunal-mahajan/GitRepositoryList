package com.gitfeaturelisting.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gitfeaturelisting.R;
import com.gitfeaturelisting.component.DynamicLoadingAdapter;
import com.gitfeaturelisting.pojo.Item;
import com.squareup.picasso.Picasso;

/**
 * Created by Kunal.Mahajan on 7/23/2018.
 */

public class RepoPaginationAdaptor extends DynamicLoadingAdapter {

    public RepoPaginationAdaptor(Context applicationContext) {
        super(applicationContext);
    }

    @Override
    protected String getEmptyText() {
        return "No repo available";
    }


    @NonNull
    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.layout_repo_info_short, parent, false);
        final RecyclerView.ViewHolder viewHolder = new RepoInfoVH(v);
        return viewHolder;
    }

    @Override
    protected void setValuesOnBind(RecyclerView.ViewHolder holder, int position) {
        Item r = (Item) records.get(position);
        RepoInfoVH rvh = (RepoInfoVH) holder;
        rvh.tvName.setText(r.getDescription());
        rvh.tvTitle.setText(r.getName());
        rvh.tvRate.setText(context.getString(R.string.updated_on) + " " + (r.getUpdatedAt()));
        Picasso.get().load(Uri.parse(r.getOwner().getAvatarUrl())).into(rvh.ivPic);
        rvh.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ActivityRepoDetail.class);
                Item r = (Item) records.get(rvh.getLayoutPosition());
                i.putExtra(ActivityRepoDetail.KEY_REPO_DETAIL, r);
                context.startActivity(i);
            }
        });
    }


    private class
    RepoInfoVH extends RecyclerView.ViewHolder {
        ImageView ivPic;
        TextView tvName;
        TextView tvTitle;
        TextView tvRate;
        LinearLayout llMain;

        public RepoInfoVH(View jobView) {
            super(jobView);
            ivPic = jobView.findViewById(R.id.layout_repo_info_short_iv_repo_pic);
            tvName = (jobView.findViewById(R.id.layout_repo_info_short_tv_name));
            tvTitle = (jobView.findViewById(R.id.layout_repo_info_short_tv_title));
            tvRate = (jobView.findViewById(R.id.layout_repo_info_short_tv_bottom));
            llMain = (jobView.findViewById(R.id.layout_repo_info_short_ll));
        }
    }
}
