package com.example.smiledlScanpressuretext;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smiledlScanpressuretext.databinding.ActivityMainBinding;

public class HomeActivity extends AppCompatActivity {

    private com.example.smiledlScanpressuretext.databinding.ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.hidScanButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SmileHidScanPressureTestActivity.class);
                startActivity(intent);
            }
        });

        binding.mipiScanButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SmileScanServicePressureTestActivity.class);
                startActivity(intent);
            }
        });
    }
}
