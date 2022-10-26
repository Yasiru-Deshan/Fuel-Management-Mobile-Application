package com.example.eadapplication;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @POST("/api/vehicle/login")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/api/vehicle/signup")
    Call<Void> executeSignup (@Body HashMap<String, String> map);

    @GET("/api/station/find/")
    Call<List<Station>> getStations();

    @GET("/api/vehicle/find/{id}/history")
    Call<List<Vehicle>> getVehicle(@Path("id") String id);

    @GET("/api/vehicle/find/{id}")
    Call<Vehicle> getVehicleDetails(@Path("id") String id);

    @GET("/api/station/find/{id}")
    Call<Station> getStationDetails(@Path("id") String id);

    @GET("/api/station/find/{id}/petrol")
    Call<List<Station>> getPetrolQueue(@Path("id") String id);

    @GET("/api/station/find/{id}/diesel")
    Call<List<Station>> getDieselQueue(@Path("id") String id);

    @PUT("/api/station/{id}/joinqueue")
    Call<Void> joinQueue(@Body HashMap<String, String> map ,@Path("id") String id);

    @PUT("api/station/{id}/exitqueue")
    Call<Void> exitQueue(@Body HashMap<String, String> map ,@Path("id") String id);

    @GET("/api/vehicle/findname")
    Call<Vehicle> getFuelBalance(@Query("name") String name);

    @PUT("/api/vehicle/pump/{id}")
    Call<Void> pumpVehicle(@Body HashMap<String, String> map ,@Path("id") String id);

    @POST("/api/station/")
    Call<Void> newStation (@Body HashMap<String, String> map);

    @POST("/api/station/login")
    Call<LoginResult> executeLoginStation(@Body HashMap<String, String> map);

    @GET("/api/vehicle/finduser")
    Call<Vehicle> getUserByEmail(@Query("email") String email);

    @PUT("/api/station/{id}")
    Call<Void> changeAvailability(@Body HashMap<String, Boolean> map ,@Path("id") String id);

    @PUT("/api/station/arrival/{id}")
    Call<Void> updateArrival(@Body HashMap<String, String> map ,@Path("id") String id);


}