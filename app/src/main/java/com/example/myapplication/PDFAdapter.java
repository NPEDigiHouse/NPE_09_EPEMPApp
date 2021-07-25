package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.ViewHolder> {
    private  final List<PDFModel> pdflist;
    private final OnItemClick onItemClick;

    public PDFAdapter(List<PDFModel> pdflist, OnItemClick onItemClick) {
        this.pdflist = pdflist;
        this.onItemClick = onItemClick;
    }
    @NonNull
    @NotNull
    @Override
    public PDFAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_rv_pdf_image, parent, false);
        return new ViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PDFAdapter.ViewHolder holder, int position) {
        holder.onBind(pdflist.get(position));
    }

    @Override
    public int getItemCount() {
        return pdflist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCover;
        public ViewHolder(@NonNull @NotNull View itemView, OnItemClick onItemClick) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.itemClicked(pdflist.get(getAdapterPosition()));
                }
            });
            imgCover = itemView.findViewById(R.id.iv_cover_item_e_references_pdf);
        }
        public void onBind(PDFModel pdf){
            Glide.with(itemView.getContext())
                    .load(pdf.getUrl())
                    .into(imgCover);
        }
    }

    public interface OnItemClick {
        void itemClicked(PDFModel pdf);
    }
}
