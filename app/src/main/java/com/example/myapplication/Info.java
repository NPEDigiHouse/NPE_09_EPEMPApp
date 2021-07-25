package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Info extends AppCompatActivity implements View.OnClickListener {

    private ImageView btnBack, btnPosko;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        btnBack = findViewById(R.id.btnBackInfo);
        btnPosko = findViewById(R.id.btnToPosko);

        btnPosko.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBackInfo:
                Intent back = new Intent(Info.this, MainActivity.class);
                back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(back);
                break;
            case R.id.btnToPosko:
                Intent posko = new Intent(Info.this, Posko.class);
                posko.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(posko);
                break;
        }
    }
}