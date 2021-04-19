package com.example.b2crentacar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.net.URL;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class RecyclerItemViewAdaptor extends RecyclerView.Adapter<RecyclerItemViewAdaptor.ViewHolder> {

    private final int PICK_IMAGE_REQUEST = 71;
    private Context context;
    private TextView type,fueltype,gear,brandAndModel,price,year,pickupdate,dropoffdate;
    private String pickupDate,dropoffDate;
    ArrayList<Car> cars=new ArrayList<>();

    public RecyclerItemViewAdaptor(Context context, ArrayList<Car> cars,String pickupDate,String dropoffDate) {
        this.cars = cars;
        this.context = context;
        this.pickupDate=pickupDate;
        this.dropoffDate=dropoffDate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cars_card_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Glide.with(context)
                .asBitmap()
                .load(cars.get(position).getPhoto())
                .into(holder.itemImage);

        type.setText(cars.get(position).getType());
        gear.setText(cars.get(position).getGear());
        fueltype.setText(cars.get(position).getFueltype());
        brandAndModel.setText(cars.get(position).getBrand()+" "+cars.get(position).getModel());
        price.setText(cars.get(position).getPrice());
        year.setText(cars.get(position).getYear());
        pickupdate.setText(pickupDate);
        dropoffdate.setText(dropoffDate);



        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage=itemView.findViewById(R.id.itemImage);
            type=itemView.findViewById(R.id.type);
            gear=itemView.findViewById(R.id.gear);
            fueltype=itemView.findViewById(R.id.fuelType);
            brandAndModel=itemView.findViewById(R.id.brandAndModel);
            price=itemView.findViewById(R.id.price);
            year=itemView.findViewById(R.id.year);
            pickupdate=itemView.findViewById(R.id.pickupDate);
            dropoffdate=itemView.findViewById(R.id.dropoffDate);

        }
    }



}
