package com.gitfeaturelisting.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gitfeaturelisting.R;
import com.gitfeaturelisting.api.APIConstancs;
import com.gitfeaturelisting.api.ApiUtils;
import com.gitfeaturelisting.component.DynamicLoadingListHelper;
import com.gitfeaturelisting.pojo.ResponseGitRepoList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kunal.Mahajan on 7/23/2018.
 */

public class ActivityHome extends AppCompatActivity {

    DynamicLoadingListHelper listHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadNewJob((LinearLayout) findViewById(R.id.ll));
    }

    private void loadNewJob(LinearLayout llNewJobs) {

        listHelper = new DynamicLoadingListHelper(this, llNewJobs, new RepoPaginationAdaptor(this)) {
            @Override
            protected void loadData(int pageNum) {
                loadRepositoryList(pageNum);
            }
        };
    }

    private void loadRepositoryList(int page) {

        ApiUtils.getAPIService().getGitRepoList(page).enqueue(new Callback<ResponseGitRepoList>() {
            @Override
            public void onResponse(Call<ResponseGitRepoList> call, Response<ResponseGitRepoList> response) {
                if (response.isSuccessful()) {
                    listHelper.dataLoaded(response.body().getItems(), response.body().getTotalCount() / APIConstancs.RESULT_COUNT);
                } else {
                    Toast.makeText(ActivityHome.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseGitRepoList> call, Throwable t) {
                Toast.makeText(ActivityHome.this, "Internet connection problem", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
