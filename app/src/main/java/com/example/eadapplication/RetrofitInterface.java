package com.example.eadapplication;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/api/vehicle/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/api/vehicle/signup")
    Call<Void> executeSignup (@Body HashMap<String, String> map);

    @GET("stations")
    Call<List<Station>> getStations();




}