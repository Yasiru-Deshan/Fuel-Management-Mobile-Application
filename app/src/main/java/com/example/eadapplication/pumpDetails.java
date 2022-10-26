package com.example.eadapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

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
        Button done = findViewById(R.id.pumpDone);

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

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pump();
            }
        });

    }

    //get balance of a vehicle

    private void checkBalance() {

        String name = findViewById(R.id.vehicleNo).toString();

        Call<Vehicle> vehicleDetails = retrofitInterface.getVehicleDetails( "635255700aef66acd7af2b0a" );

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
                Toast.makeText(pumpDetails.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });


    }

    //pump a vehicle
    private void pump() {

        HashMap<String, String> map = new HashMap<>();

        final TextInputEditText quantity = findViewById(R.id.quantity);

        map.put("quantity", quantity.getText().toString());
        map.put("station", "Welfare Association SL");

        Call<Void> call = retrofitInterface.pumpVehicle(map, "635255700aef66acd7af2b0a");

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.code() == 200) {
                    Toast.makeText(pumpDetails.this,
                            "Fuel capacity updated", Toast.LENGTH_LONG).show();
                    Intent activity2Intent = new Intent(getApplicationContext(), AdminHome.class);
                    startActivity(activity2Intent);
                } else if (response.code() == 400) {
                    Toast.makeText(pumpDetails.this,
                            "Something Wrong", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(pumpDetails.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });





    }

}
