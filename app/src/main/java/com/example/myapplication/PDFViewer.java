package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PDFViewer extends AppCompatActivity {

    private PDFView pdfView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        pdfView = findViewById(R.id.pdfView);

        String getPdfName = getIntent().getStringExtra("namePdf");
        Log.d("haha", getPdfName);
        getPDF(getPdfName);


    }

    private void getPDF(String s) {
        DatabaseReference myref = database.getReference("PDF").child(s);
        myref.addValueEventListener(new ValueEventListener() {
            class RetrivePdfStream extends AsyncTask<String, Void, InputStream> {
                @Override
                protected InputStream doInBackground(String... strings) {
                    InputStream inputStream = null;
                    try {
                        URL uRl = new URL(strings[0]);
                        HttpURLConnection urlConnection = (HttpURLConnection)uRl.openConnection();
                        if (urlConnection.getResponseCode() == 200){
                            inputStream = new BufferedInputStream(urlConnection.getInputStream());
                        }
                    }catch (IOException e){
                        return null;
                    }
                    return inputStream;
                }

                @Override
                protected void onPostExecute(InputStream inputStream) {
                    pdfView.fromStream(inputStream).load();
                }
            }

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String url = snapshot.child("url").getValue().toString();
                new RetrivePdfStream().execute(url);
                Toast.makeText(PDFViewer.this, "please wait a few second", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}