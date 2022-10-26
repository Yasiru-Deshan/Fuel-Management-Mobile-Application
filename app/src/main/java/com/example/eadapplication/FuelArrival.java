package com.example.eadapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FuelArrival extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:8070";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_arrival);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        findViewById(R.id.pumpDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateArrival();
            }
        });
    }

    //update arrival details
    private void updateArrival() {

        final TextInputEditText fuelType = findViewById(R.id.fuelT);
        final TextInputEditText quantity = findViewById(R.id.quantity);
        final TextInputEditText arrivalTime = findViewById(R.id.arrivalTime);

                HashMap<String, String> map = new HashMap<>();

                map.put("arrivalType", fuelType.getText().toString());
                map.put("arrivalDate", arrivalTime.getText().toString());
                map.put("arrivalQuantity", quantity.getText().toString());

                Call<Void> call = retrofitInterface.updateArrival(map, "6352f9565293d865e7168c16");

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.code() == 200) {
                            Toast.makeText(FuelArrival.this,
                                    "Arrival Details Updated", Toast.LENGTH_LONG).show();
                        } else if (response.code() == 400) {
                            Toast.makeText(FuelArrival.this,
                                    "Details couldn't updated", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(FuelArrival.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });


    }

}