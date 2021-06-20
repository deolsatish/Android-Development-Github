package com.example.realestateapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourismapp.R;
import com.example.tourismapp.touristplace;

import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>{
    public List<touristplace> placeslist;
    public Context context;
    private OnRowClickListener listener;

    public PropertyAdapter(List<touristplace> placeslist, Context context, OnRowClickListener clickListener) {
        this.placeslist = placeslist;
        this.context = context;
        this.listener = clickListener;
    }
    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.vertical_view,parent,false);
        return new PropertyViewHolder(itemView, listener);
    }

    public interface OnRowClickListener {
        void onItemClick (int position);
    }
    public class PropertyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView verticalimageView;
        public TextView addressTextView,titleTextView,ppaTextView;
        public OnRowClickListener onRowClickListener;

        public PropertyViewHolder(@NonNull View itemView, OnRowClickListener onRowClickListener) {
            super(itemView);
            verticalimageView=itemView.findViewById(R.id.verticalImageView);
            addressTextView=itemView.findViewById(R.id.addressView);
            titleTextView=itemView.findViewById(R.id.titleView);
            ppaTextView=itemView.findViewById(R.id.ppaView);
            this.onRowClickListener = onRowClickListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            onRowClickListener.onItemClick(getAdapterPosition());
        }
    }
    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position) {
        holder.verticalimageView.setImageResource(placeslist.get(position).getImage());
        holder.addressTextView.setText(placeslist.get(position).getAddress());
        holder.titleTextView.setText(placeslist.get(position).getTitle());
        holder.ppaTextView.setText(placeslist.get(position).getPpa());
    }
    @Override
    public int getItemCount() {
        return placeslist.size();
    }
}
