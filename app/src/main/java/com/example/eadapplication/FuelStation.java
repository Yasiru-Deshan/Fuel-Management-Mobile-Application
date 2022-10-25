package com.example.eadapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FuelStation extends AppCompatActivity {

    private TextView stationId;
    private TextView stationName;
    private TextView stationLocation;
    private TextView nextDate;
    private TextView available;
    private TextView vehiCount;
    private TextView waitingTime;
    private TextView dcount;
    private TextView dhours;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:8070";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station);

        Button buttonOne = findViewById(R.id.join);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent activity2Intent = new Intent(getApplicationContext(), queue.class);
                startActivity(activity2Intent);
            }
        });

        stationId = findViewById(R.id.stationId);
        stationName = findViewById(R.id.stationName);
        stationLocation = findViewById(R.id.stationLocation);
        nextDate = findViewById(R.id.nextDate);
        available = findViewById(R.id.available);
        vehiCount = findViewById(R.id.vehiCount);
        waitingTime = findViewById(R.id.waitingTime);
        dcount = findViewById(R.id.dcount);
        dhours = findViewById(R.id.dhours);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<Station> stationDetails = retrofitInterface.getStationDetails();
        Call<List<Station>> petrolQueue = retrofitInterface.getPetrolQueue();
        Call<List<Station>> dieselQueue = retrofitInterface.getDieselQueue();

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

        petrolQueue.enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                if (!response.isSuccessful()) {

                    return;
                }

                List<Station> vehicles = response.body();

                vehiCount.setText(String.valueOf(vehicles.size()));


            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                vehiCount.setText(t.getMessage());
            }
        });

        dieselQueue.enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                if (!response.isSuccessful()) {

                    return;
                }

                List<Station> vehicles = response.body();

                dcount.setText(String.valueOf(vehicles.size()));


            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                dcount.setText(t.getMessage());
            }
        });
    }
}