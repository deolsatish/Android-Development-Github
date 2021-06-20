package com.example.tourismapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder> {

    public List<com.example.tourismapp.touristplace> placeslist;
    public Context context;

    public HorizontalAdapter(List<touristplace> placeslist, Context context) {
        this.placeslist = placeslist;
        this.context = context;
    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.horizontal_view,parent,false);
        return new HorizontalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {
        holder.horizontalimageView.setImageResource(placeslist.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return placeslist.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        public ImageView horizontalimageView;


        public HorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontalimageView=itemView.findViewById(R.id.horizontalimageview);
        }
    }
}
