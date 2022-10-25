package com.example.eadapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class pumpDetails extends AppCompatActivity {

    private TextView fbalance;
    private TextView fuel;
    private TextView remaining;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:8070";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pump_details);

        fbalance = findViewById(R.id.fbalance);
        fuel = findViewById(R.id.fbalance);
        Button check = findViewById(R.id.check);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBalance();
            }
        });

    }


    private void checkBalance() {

                Call<Vehicle> vehicleDetails = retrofitInterface.getVehicleDetails();

                vehicleDetails.enqueue(new Callback<Vehicle>() {
                    @Override
                    public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                        if (!response.isSuccessful()) {

                            return;
                        }

                        Vehicle vehicle = response.body();

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(pumpDetails.this);
                        builder1.setTitle(vehicle.getBalance());
                        builder1.setMessage(vehicle.getFuelType());

                        builder1.show();
                    }

                    @Override
                    public void onFailure(Call<Vehicle> call, Throwable t) {

                    }
                });


            }

}
