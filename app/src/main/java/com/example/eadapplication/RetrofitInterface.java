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

    @GET("/api/station/find/")
    Call<List<Station>> getStations();

    @GET("/api/vehicle/find/635255700aef66acd7af2b0a/history")
    Call<List<Vehicle>> getVehicle();

    @GET("/api/vehicle/find/635255700aef66acd7af2b0a")
    Call<Vehicle> getVehicleDetails();

    @GET("/api/station/find/635252122711e4bc41f32f66")
    Call<Station> getStationDetails();

    @GET("/api/station/find/635252122711e4bc41f32f66/petrol")
    Call<List<Station>> getPetrolQueue();

    @GET("/api/station/find/635252122711e4bc41f32f66/diesel")
    Call<List<Station>> getDieselQueue();



}