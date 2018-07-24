package com.gitfeaturelisting.pojo;

/**
 * Created by Kunal.Mahajan on 7/23/2018.
 */


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class License implements Serializable{

	@SerializedName("name")
	private String name;

	@SerializedName("spdx_id")
	private String spdxId;

	@SerializedName("key")
	private String key;

	@SerializedName("url")
	private String url;

	@SerializedName("node_id")
	private String nodeId;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public String getSpdxId() {
		return spdxId;
	}

	public String getKey() {
		return key;
	}

	public String getUrl() {
		return url;
	}

	public String getNodeId() {
		return nodeId;
	}
}