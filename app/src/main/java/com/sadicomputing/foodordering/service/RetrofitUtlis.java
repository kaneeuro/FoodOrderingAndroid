package com.sadicomputing.foodordering.service;

/**
 * Created by modykane on 22/10/2017.
 */

public class RetrofitUtlis {

    private RetrofitUtlis() {}

    public static final String BASE_URL = "http://192.168.1.19:8080/";
    public static final String BASE_URL_LOCALHOST = "http://10.0.2.2:8080/";

    public static RetrofitService getRetrofitService() {

        return RetrofitClient.getClient(BASE_URL).create(RetrofitService.class);
    }
}
