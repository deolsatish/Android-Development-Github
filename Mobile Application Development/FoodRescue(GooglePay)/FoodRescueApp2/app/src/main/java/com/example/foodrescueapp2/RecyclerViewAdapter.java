package com.example.foodrescueapp2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrescueapp2.model.Food;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Food> foodList;
    private Context context;
    private OnRowClickListener listener;


    public RecyclerViewAdapter(List<Food> foodList, Context context, OnRowClickListener clickListener) {
        this.foodList = foodList;
        this.context = context;
        this.listener=clickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.food_row,parent,false);

        return new ViewHolder(itemView,context,listener);
    }
    public interface OnRowClickListener {
        void onItemClick (int position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Log.i("OnBindViewHolder","working");

        holder.title.setText("Title: "+foodList.get(position).getTitle());
        holder.description.setText("Description :"+foodList.get(position).getDescription());
        holder.date.setText("Date: "+foodList.get(position).getDate());
        holder.pickuptime.setText("Pick Up Time: "+foodList.get(position).getPickuptime());
        holder.quantity.setText("Quantity: "+foodList.get(position).getQuantity());
        holder.location.setText("Location: "+foodList.get(position).getLocation());
        holder.price.setText("Price(AUD): "+foodList.get(position).getPrice()+"$");
        if(foodList.get(position).getImage()!=null)
        {
            String encodedImageString = Base64.encodeToString(foodList.get(position).getImage(), Base64.DEFAULT);
            byte[] bytarray = Base64.decode(encodedImageString, Base64.DEFAULT);
            Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0,
                    bytarray.length);
            holder.imageView.setImageBitmap(bmimage);
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_SUBJECT,"Restaurant");
                    intent.putExtra(Intent.EXTRA_TEXT,"Try Food " +foodList.get(position).getTitle()+" .It is tasty and delicious. "+foodList.get(position).getDescription());
                    intent.setType("text/plain");
                    context.startActivity(Intent.createChooser(intent, "Send To"));
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title,description,date,pickuptime,quantity,location,price;
        ImageView imageView;
        ImageButton imageButton;
        public OnRowClickListener onRowClickListener;

        public ViewHolder(@NonNull View itemView,Context context, OnRowClickListener onRowClickListener) {
            super(itemView);
            title=itemView.findViewById(R.id.titleView);
            description=itemView.findViewById(R.id.descriptionView);
            date=itemView.findViewById(R.id.dateView);
            pickuptime=itemView.findViewById(R.id.pickuptimeView);
            quantity=itemView.findViewById(R.id.quantityView);
            location=itemView.findViewById(R.id.locationView);
            price=itemView.findViewById(R.id.priceView);
            imageView=itemView.findViewById(R.id.imageView);
            imageButton=itemView.findViewById(R.id.button);



            this.onRowClickListener = onRowClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRowClickListener.onItemClick(getAdapterPosition());
        }
    }
}
