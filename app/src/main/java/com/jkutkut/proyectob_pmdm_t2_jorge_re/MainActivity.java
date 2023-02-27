package com.jkutkut.proyectob_pmdm_t2_jorge_re;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;

import com.jkutkut.proyectob_pmdm_t2_jorge_re.custom.CustomActivity;

public class MainActivity extends CustomActivity {

    private AppCompatButton btnStart;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(v -> {
//            Intent i = new Intent(this, QueryActivity.class);
//            startActivity(i);
        });
    }
}