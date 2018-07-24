package com.gitfeaturelisting.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gitfeaturelisting.R;
import com.gitfeaturelisting.pojo.Item;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityRepoDetail extends AppCompatActivity {

    public static final String KEY_REPO_DETAIL = "KEY_REPO_DETAIL";

    @BindView(R.id.activity_repo_detail_iv_pic)
    ImageView ivPic;

    @BindView(R.id.activity_repo_detail_tv_description)
    TextView tvDesc;

    @BindView(R.id.activity_repo_detail_tv_owner_type)
    TextView tvOwnerType;

    @BindView(R.id.activity_repo_detail_tv_login)
    TextView tvLogin;

    @BindView(R.id.activity_repo_detail_tv_name)
    TextView tvName;

    @BindView(R.id.activity_repo_detail_ll_info)
    LinearLayout llInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_detail);
        ButterKnife.bind(this);
        Item i = (Item) getIntent().getSerializableExtra(KEY_REPO_DETAIL);
        setTitle("Repository Details");
        Picasso.get().load(Uri.parse(i.getOwner().getAvatarUrl())).into(ivPic);
        tvLogin.setText(i.getOwner().getLogin());
        tvName.setText(i.getName());
        tvOwnerType.setText(i.getOwner().getType());
        tvDesc.setText(i.getDescription());
        addInfo("Forks", "" + i.getForks());
        addInfo("Open Issues", "" + i.getOpenIssues());
        addInfo("Watchers", "" + i.getWatchers());
        addInfo("Score", "" + i.getScore());
        addInfo("Owner ID", "" + i.getOwner().getId());
        addInfo("Licence", "" + i.getLicense().getName());
        addInfo("Created at", "" + i.getCreatedAt());
        addInfo("Updated at", "" + i.getUpdatedAt());
        addInfo("Size", "" + i.getSize());
        addInfo("Has wiki", "" + i.isHasWiki());
    }

    private void addInfo(String title, String info) {
        View v = LayoutInflater.from(ActivityRepoDetail.this).inflate(R.layout.layout_title_info, null);
        ((TextView) v.findViewById(R.id.layout_title_info_title)).setText(title);
        ((TextView) v.findViewById(R.id.layout_title_info_info)).setText(info);
        llInfo.addView(v);
    }
}
