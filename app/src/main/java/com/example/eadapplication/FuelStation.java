package com.example.eadapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
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
    private Button join;
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
        join = findViewById(R.id.join);
        dcount = findViewById(R.id.dcount);
        dhours = findViewById(R.id.dhours);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<Station> stationDetails = retrofitInterface.getStationDetails("635252122711e4bc41f32f66");
        Call<List<Station>> petrolQueue = retrofitInterface.getPetrolQueue("635252122711e4bc41f32f66");
        Call<List<Station>> dieselQueue = retrofitInterface.getDieselQueue("635252122711e4bc41f32f66");

        findViewById(R.id.join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinToQueue();
            }
        });

        //get station details
        stationDetails.enqueue(new Callback<Station>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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

               // String dateStr = station.getArrivalDate().toString();
               // SimpleDateFormat sdf = new SimpleDateFormat(“yyyy-MM-dd HH:mm:ss”);
                // Date birthDate = sdf. parse(dateStr); //then user.02-Apr-2013

                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
                //LocalDate fdate = LocalDate.parse(station.getArrivalDate().toString(), formatter);
                //nextDate.setText(fdate);

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

        //get petrol queue
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

        //get diesel queue
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

    //join to queue method
    private void joinToQueue() {

        HashMap<String, String> map = new HashMap<>();

        map.put("name", "BHP 3812");
        map.put("balance", "8");
        map.put("vehicleId", "6356d775170bc939133eb3f2");
        map.put("fuelType", "Petrol");

        Call<Void> call = retrofitInterface.joinQueue(map, "635252122711e4bc41f32f66");

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.code() == 200) {
                    Toast.makeText(FuelStation.this,
                            "Joined to queue", Toast.LENGTH_LONG).show();
                    Intent activity2Intent = new Intent(getApplicationContext(), queue.class);
                    startActivity(activity2Intent);
                } else if (response.code() == 400) {
                    Toast.makeText(FuelStation.this,
                            "Something Wrong", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(FuelStation.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });





    }

}