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

public class Home extends AppCompatActivity {

    private TextView stationDetails;
    private TextView vehiNu;
    private Button balance;
    private TextView history;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:8070";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button buttonOne = findViewById(R.id.eligiblebutton);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent activity2Intent = new Intent(getApplicationContext(), FuelStation.class);
                startActivity(activity2Intent);
            }
        });

        stationDetails = findViewById(R.id.stationDetails);
        history = findViewById(R.id.used);
        vehiNu = findViewById(R.id.vehiNu);
        balance = findViewById(R.id.balanceButton);


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<List<Station>> stationsList = retrofitInterface.getStations();
        Call<List<Vehicle>> vehicleList = retrofitInterface.getVehicle();
        Call<Vehicle> vehicleDetails = retrofitInterface.getVehicleDetails();

        vehicleDetails.enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                if (!response.isSuccessful()) {

                    return;
                }

                Vehicle vehicle = response.body();

                balance.setText(vehicle.getBalance()) ;
                vehiNu.setText(vehicle.getVehicleNumber());


            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
                balance.setText(t.getMessage());
            }
        });

        vehicleList.enqueue(new Callback<List<Vehicle>>() {
            @Override
            public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                if (!response.isSuccessful()) {

                    return;
                }

                List<Vehicle> vehicles = response.body();


                for (Vehicle vehicle : vehicles) {
                    String content = "";
                    content += "Quota Used: " + vehicle.getQuantity() + "\n";
                    content += "Station: " + vehicle.getStation() +  "\n";
                    content += "\n";

                    history.append(content);

                }

            }

            @Override
            public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                history.setText(t.getMessage());
            }
        });

       stationsList.enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                if (!response.isSuccessful()) {

                    return;
                }

                List<Station> stations = response.body();


                for (Station station : stations) {
                    String content = "";
                   content += "ID: " + station.getStationCode() + "\n";
                    content += "Name: " + station.getName() + "\n";
                    content += "Location: " + station.getLocation() + "\n";
                    content += "\n";

                    stationDetails.append(content);

                }

            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                stationDetails.setText(t.getMessage());
            }
        });
    }
}