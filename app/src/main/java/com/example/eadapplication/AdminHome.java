package com.example.eadapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminHome extends AppCompatActivity {

    private TextView stationId;
    private TextView stationName;
    private TextView stationLocation;
    private TextView nextDate;
    private TextView available;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:8070";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Button buttonOne = findViewById(R.id.nextButton);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent activity2Intent = new Intent(getApplicationContext(), FuelArrival.class);
                startActivity(activity2Intent);
            }
        });

        Button buttonTwo = findViewById(R.id.pumpButton);
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent activity2Intent = new Intent(getApplicationContext(), pumpDetails.class);
                startActivity(activity2Intent);
            }
        });

        stationId = findViewById(R.id.stationId);
        stationName = findViewById(R.id.stationName);
        stationLocation = findViewById(R.id.stationLocation);
        nextDate = findViewById(R.id.nextDate);
        available = findViewById(R.id.available);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<Station> stationDetails = retrofitInterface.getStationDetails("635252122711e4bc41f32f66");

        stationDetails.enqueue(new Callback<Station>() {
            @Override
            public void onResponse(Call<Station> call, Response<Station> response) {
                if (!response.isSuccessful()) {

                    return;
                }

                Station station = response.body();

                stationId.setText(station.getStationCode()) ;
                stationName.setText(station.getName());
                stationLocation.setText(station.getLocation());
                nextDate.setText(station.getArrivalDate().toString());


                if(station.isAvailable()==true){
                    available.setText("Available");
                }else{
                    available.setText("Not Available");
                }
            }

            @Override
            public void onFailure(Call<Station> call, Throwable t) {
                stationName.setText(t.getMessage());
            }
        });
    }
}