package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Handsanitizer extends AppCompatActivity implements View.OnClickListener {

    private Button btnPdf;
    private ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handsanitizer);

        btnPdf = findViewById(R.id.btnPdf);
        btnBack = findViewById(R.id.btnBackHdr);


        btnPdf.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPdf:
                Intent toPdf = new Intent(Handsanitizer.this, PDFViewer.class);
                String namePdf = "Handsanitizer";
                toPdf.putExtra("namePdf", namePdf);
                toPdf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(toPdf);
                break;
            case R.id.btnBackHdr:
                Intent back = new Intent(Handsanitizer.this, MainActivity.class);
                back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(back);
                break;
        }
    }
}