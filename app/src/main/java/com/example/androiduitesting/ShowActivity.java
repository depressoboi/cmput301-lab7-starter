package com.example.androiduitesting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        // Get the city name from the intent
        Intent intent = getIntent();
        String cityName = intent.getStringExtra("CITY_NAME");

        // Display city name
        TextView cityTextView = findViewById(R.id.textView_cityName);
        cityTextView.setText(cityName);

        // Set up back button
        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the current activity and return to MainActivity
                finish();
            }
        });
    }
}
