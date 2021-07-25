package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PDFList extends AppCompatActivity implements PDFAdapter.OnItemClick {

    private ImageView btnBack;
    private RecyclerView rvPdf;
    private List<PDFModel> pdflist;
    private PDFAdapter pdfAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdflist);

        btnBack = findViewById(R.id.btnBackListPdf);
        pdflist = new ArrayList<>();
        pdfAdapter = new PDFAdapter(pdflist, this);

        rvPdf = findViewById(R.id.rvPDF);
        rvPdf.setLayoutManager(new GridLayoutManager(this, 2));
        rvPdf.setAdapter(pdfAdapter);

        updaePdfList();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(PDFList.this, MainActivity.class);
                back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(back);
            }
        });
    }

    private void updaePdfList() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("PDFImage");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    System.out.println(data.getKey()); // debug

                    PDFModel pdf = new PDFModel();
                    pdf.setUrl(data.child("url").getValue().toString());
                    pdf.setKey(data.getKey());

                    pdflist.add(pdf);
                }
                pdfAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public void itemClicked(PDFModel pdf) {
        Intent goToPDfViewer = new Intent(PDFList.this, PDFViewer.class);
        goToPDfViewer.putExtra("namePdf", pdf.getKey());
        goToPDfViewer.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(goToPDfViewer);
    }
}