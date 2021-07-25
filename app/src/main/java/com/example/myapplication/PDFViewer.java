package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
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
    private ImageView btnBack;
    private String getPdfTitle;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        pdfView = findViewById(R.id.pdfView);
        btnBack = findViewById(R.id.btnBackPdfViewer);

        sharedPreferences = getSharedPreference(PDFViewer.this);

        getPdfTitle = new String();

        String getPdfName = getIntent().getStringExtra("namePdf");
        Log.d("haha", getPdfName);

        getPDF(getPdfName);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToList = new Intent(PDFViewer.this, PDFList.class);
                backToList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(backToList);
            }
        });

    }

    private void getPDF(String s) {
        getPdfTitle = s;
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
//                    int pageNumber = Integer.parseInt(getSharedPreference(PDFViewer.this).getString(getIntent().getStringExtra("namePdf"), ""));
                    String lala = sharedPreferences.getString(getIntent().getStringExtra("namePdf"), "0");

                    Log.d("debugPreference",lala);
                    Log.d("debugIntentExtra", getIntent().getStringExtra("namePdf"));
                    pdfView.fromStream(inputStream).defaultPage(Integer.parseInt(lala)).load();
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

    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("helo", String.valueOf(pdfView.getCurrentPage()));
        editor.putString(getIntent().getStringExtra("namePdf"), String.valueOf(pdfView.getCurrentPage()));
        Log.d("debugCurrentPage", String.valueOf(pdfView.getCurrentPage()));
        editor.apply();
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        SharedPreferences.Editor editor = getSharedPreference(PDFViewer.this).edit();
//        editor.putString(getPdfTitle, String.valueOf(pdfView.getCurrentPage()));
//    }
}