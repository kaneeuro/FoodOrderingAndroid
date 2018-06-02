package com.sadic.formationandroid.services;

import com.sadic.formationandroid.entities.Customer;
import com.sadic.formationandroid.entities.Invoice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by modykane on 22/10/2017.
 */

public interface RetrofitService {

    @POST("customerbylogin")
    @FormUrlEncoded
    Call<Customer> login(@Field("login") String login);

    @POST("invoicesbycustomer")
    @FormUrlEncoded
    Call<List<Invoice>> getInvoicesByCustomer(@Field("login") String login);

    @GET("invoices/{id}")
    Call<Invoice> getInvoiceById(@Path("id") Long id);

    @PUT("invoices/{id}")
    Call<Invoice> paymentInvoice(@Path("id") Long id);
}
