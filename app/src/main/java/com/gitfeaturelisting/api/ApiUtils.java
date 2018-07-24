package com.gitfeaturelisting.api;

/**
 * Created by Kunal.Mahajan on 7/23/2018.
 */


public class ApiUtils {

    public static BaseApiService getAPIService() {
        return RetrofitClient.getClient(APIConstancs.BASE_URL_API).create(BaseApiService.class);
    }
}
