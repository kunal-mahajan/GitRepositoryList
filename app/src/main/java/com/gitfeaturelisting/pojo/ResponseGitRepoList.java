package com.gitfeaturelisting.pojo;

/**
 * Created by Kunal.Mahajan on 7/23/2018.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseGitRepoList implements Serializable {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private List<Item> items;

    public int getTotalCount() {
        return totalCount;
    }

    public List<Item> getItems() {
        return items;
    }

}