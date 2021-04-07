package com.example.b2crentacar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;

public class AddCarActivity extends AppCompatActivity {
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private ImageView carImage;
    private Button addCar;
    private EditText plateNumber,brand,model,price,year;
    private String PlateNumber,Brand,Model,Price,Year;
    private RadioGroup radioGroupType, radioGroupGear, radioGroupFuelType;
    private RadioButton RadioButtonType, RadioButtonGear, RadioButtonFuelType;
    private String RadioTypeText, RadioGearText, RadioFuelTypeText;
    private Button addCarBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        carImage=findViewById(R.id.carImage);
        plateNumber = findViewById(R.id.txtPlateNo);
        brand = findViewById(R.id.txtBrand);
        model = findViewById(R.id.txtModel);
        price = findViewById(R.id.txtPrice);
        year = findViewById(R.id.txtYear);
        radioGroupType = findViewById(R.id.radioGroup3);
        radioGroupGear = findViewById(R.id.radioGroup);
        radioGroupFuelType = findViewById(R.id.radioGroup5);
        addCarBtn = (Button) findViewById(R.id.btnAddCar);



        carImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        addCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlateNumber = plateNumber.getText().toString();
                Brand = brand.getText().toString();
                Model = model.getText().toString();
                Price = price.getText().toString();
                Year = year.getText().toString();

                int selectedIdType = radioGroupType.getCheckedRadioButtonId();
                RadioButtonType = findViewById(selectedIdType);
                RadioTypeText = RadioButtonType.getText().toString();

                int selectedIdGear = radioGroupGear.getCheckedRadioButtonId();
                RadioButtonGear = findViewById(selectedIdGear);
                RadioGearText = RadioButtonGear.getText().toString();

                int selectedIdFuelType = radioGroupFuelType.getCheckedRadioButtonId();
                RadioButtonFuelType = findViewById(selectedIdFuelType);
                RadioFuelTypeText = RadioButtonFuelType.getText().toString();

                if(checkInput()){
                    //do the db stuffs

                }

            }
        });

    }

    private boolean checkInput(){
        if(!TextUtils.isEmpty(PlateNumber)){
            if(!TextUtils.isEmpty(Brand)){
                if(!TextUtils.isEmpty(Model)){
                    if(!TextUtils.isEmpty(Price)){
                        if(!TextUtils.isEmpty(Year)){
                            return true;
                        }else{
                            year.setError("Please Enter Year");
                        }
                    }else{
                        price.setError("Please Enter Price");
                    }
                }else{
                    model.setError("Please Enter Model");
                }
            }else{
                brand.setError("Please Enter Brand");
            }
        }else{
            plateNumber.setError("Please Enter Plate Number");
        }

        return false;

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(AddCarActivity.this.getContentResolver(), filePath);
                carImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }



}