package com.gitfeaturelisting.api;

/**
 * Created by Kunal.Mahajan on 7/23/2018.
 */


import com.gitfeaturelisting.pojo.ResponseGitRepoList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BaseApiService {

    @GET("search/repositories?q=android%20&sort=stars&order=desc&per_page=" + APIConstancs.RESULT_COUNT)
    Call<ResponseGitRepoList> getGitRepoList(@Query("page") int page);
}
