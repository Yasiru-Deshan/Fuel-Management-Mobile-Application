package com.example.eadapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class queue extends AppCompatActivity {

    private TextView stationId;
    private TextView stationName;
    private TextView stationLocation;
    private TextView nextDate;
    private TextView available;
    private TextView vehiCount;
    private TextView waitingTime;
    private TextView dcount;
    private TextView dhours;
    private Button exit;
    private Button done;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:8070";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);
        Button buttonOne = findViewById(R.id.done);
        buttonOne.setOnClickListener(new View.OnClickListener() {
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
        vehiCount = findViewById(R.id.vehiCount);
        waitingTime = findViewById(R.id.waitingTime);
        dcount = findViewById(R.id.dcount);
        dhours = findViewById(R.id.dhours);
        exit = findViewById(R.id.exit);
        done = findViewById(R.id.done);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<Station> stationDetails = retrofitInterface.getStationDetails("635252122711e4bc41f32f66");
        Call<List<Station>> petrolQueue = retrofitInterface.getPetrolQueue("635252122711e4bc41f32f66");
       // Call<List<Station>> dieselQueue = retrofitInterface.getDieselQueue();

        findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitQueue();
            }
        });

        findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitQueue();
            }
        });

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

       /* dieselQueue.enqueue(new Callback<List<Station>>() {
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
                dcount.setText(t.getMessage());
            }
        });*/

    }

    private void exitQueue() {

        HashMap<String, String> map = new HashMap<>();

        map.put("vehicleId", "6356d775170bc939133eb3f2");
        map.put("fuelType", "Petrol");

        Call<Void> call = retrofitInterface.exitQueue(map, "635252122711e4bc41f32f66");

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.code() == 200) {
                    Toast.makeText(queue.this,
                            "Exited from queue", Toast.LENGTH_LONG).show();
                    Intent activity2Intent = new Intent(getApplicationContext(), Home.class);
                    startActivity(activity2Intent);
                } else if (response.code() == 400) {
                    Toast.makeText(queue.this,
                            "Something Wrong", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(queue.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });





    }
}