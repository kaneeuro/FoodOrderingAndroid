package com.sadicomputing.foodordering.service;

/**
 * Created by modykane on 22/10/2017.
 */

public class RetrofitUtlis {

    private RetrofitUtlis() {}

    public static final String BASE_URL = "http://192.168.1.18:8080/";
    public static final String BASE_URL_LOCALHOST = "http://10.0.2.2:8080/";
    public static final String BASE_URL_WEB = "http://www.sadicomputing.com/FoodOrdering/web/";

    public static RetrofitService getRetrofitService() {

        return RetrofitClient.getClient(BASE_URL_WEB).create(RetrofitService.class);
    }
}
