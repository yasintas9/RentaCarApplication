package com.example.b2crentacar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RecyclerItemReservedViewAdapter extends RecyclerView.Adapter<RecyclerItemReservedViewAdapter.ViewHolder> {

    private final int PICK_IMAGE_REQUEST = 71;
    private Context context;
    private TextView type,fueltype,gear,brandAndModel,price,year,pickupdate,dropoffdate;
    private String pickupDate,dropoffDate;
    ArrayList<Car> cars;
    ArrayList<Dates> dates;
    private DatabaseReference database;
    private FirebaseAuth fAuth;

    public RecyclerItemReservedViewAdapter(Context context, ArrayList<Car> cars,ArrayList<Dates> dates) {
        this.cars = cars;
        this.dates=dates;
        this.context = context;
        database = FirebaseDatabase.getInstance().getReference();
        fAuth=FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reserved_cars_view,parent,false);
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
        year.setText(cars.get(position).getYear());
        pickupdate.setText(dates.get(position).getPickUp());
        dropoffdate.setText(dates.get(position).getDrop());

        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        String inputString1 =dates.get(position).getPickUp();
        String inputString2 = dates.get(position).getDrop();
        long days=0;
        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            long diff = date2.getTime() - date1.getTime();
            days =TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        price.setText(String.valueOf(Long.parseLong(cars.get(position).getPrice())*days)+" TL");

        holder.payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure? You are going to do the payment");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(context,PaymentActivity.class);
                        context.startActivity(intent);
                        ((Activity)context).finish();

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView payment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage=itemView.findViewById(R.id.itemImage1);
            type=itemView.findViewById(R.id.type1);
            gear=itemView.findViewById(R.id.gear1);
            fueltype=itemView.findViewById(R.id.fuelType1);
            brandAndModel=itemView.findViewById(R.id.brandAndModel1);
            price=itemView.findViewById(R.id.price1);
            year=itemView.findViewById(R.id.year1);
            pickupdate=itemView.findViewById(R.id.pickupDate1);
            dropoffdate=itemView.findViewById(R.id.dropoffDate1);
            payment=itemView.findViewById(R.id.paymentforCar);

        }
    }



}
