package com.example.foodrescueapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.foodrescueapp2.data.DatabaseHelper;
import com.example.foodrescueapp2.model.Food;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import static com.example.foodrescueapp2.R.drawable.img_meal_two;

public class addfoodActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 0;
    String titlestr,descriptionstr,datestr,timestr,quantitystr,locationstr,pricestr,imageuristr;
    byte[] image;
    DatabaseHelper db;
    String username;
    Button saveButton;
    Bitmap bitmaps;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        saveButton=findViewById(R.id.saveButton);

        if(grantResults.length>0 &&grantResults[0]== PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                ImageView imageView=findViewById(R.id.imageview);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectImage();
                    }
                });


            }
        }
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfood);
        db = new DatabaseHelper(this);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");

        ImageView imageView=findViewById(R.id.imageview);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        else
        {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectImage();
                }
            });

        }





        Button saveButton=findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title,description,location,quantity, price;
                title=findViewById(R.id.titleview);
                description=findViewById(R.id.descriptionview);
                location=findViewById(R.id.locationview);
                quantity=findViewById(R.id.quantityview);
                DatePicker datePicker=findViewById(R.id.dateview);
                TimePicker timePicker=findViewById(R.id.timeview);
                ImageView imageView=findViewById(R.id.imageview);
                imageView=findViewById(R.id.imageview);
                Bitmap bitmaps = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                price=findViewById(R.id.priceView);
                if(!isNumeric(price.getText().toString()))
                {
                    Toast.makeText(addfoodActivity.this,"Enter floating Numbers, for now it wil set to default i.e Zero" ,
                            Toast.LENGTH_LONG).show();
                    price.setText("0");
                }

                titlestr=title.getText().toString();
                descriptionstr=description.getText().toString();
                locationstr=location.getText().toString();
                quantitystr=quantity.getText().toString();
                pricestr=price.getText().toString();

                datestr=("Date: "+ datePicker.getDayOfMonth()+"/"+ (datePicker.getMonth())+"/"+datePicker.getYear());
                timestr=("PickUp Time: "+ timePicker.getHour()+"."+ (timePicker.getMinute())+"."+"00");
                Log.i("Save Button check","Working");
                long result=db.insertFood(new Food(username,titlestr,descriptionstr,datestr,timestr,quantitystr,locationstr,pricestr,image));
                if (result > 0)
                {
                    Toast.makeText(addfoodActivity.this, "Saved successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(addfoodActivity.this, "Save Unsuccessful!", Toast.LENGTH_SHORT).show();
                }
            }
        });






    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            InputStream is = null;
            try {
                is = getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            ImageView imageView=findViewById(R.id.imageview);
            imageView.setImageBitmap(bitmap);


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            image=stream.toByteArray();
            Log.i("bitmap",bitmap.toString());

        }

    }
    private void selectImage() {

        /*Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);*/


        Intent in = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        in.putExtra("crop", "true");
        in.putExtra("outputX", 100);
        in.putExtra("outputY", 100);
        in.putExtra("scale", true);
        in.putExtra("return-data", true);
        startActivityForResult(in, 1);
    }
    public String createImageFromBitmap(Bitmap bitmap) {
        String fileName = "myImage";//no .png or .jpg needed
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }

    public void Objbtnfunction(View view) {
        Intent intent = new Intent(addfoodActivity.this, ObjectDetectionActivity.class);
        Log.i("Sneding Bitmap","SUCCESFULL");
        ImageView image=findViewById(R.id.imageview);
        Bitmap bitmaps1 = ((BitmapDrawable)image.getDrawable()).getBitmap();

        intent.putExtra("filename", createImageFromBitmap(bitmaps1));
        startActivity(intent);
        finish();
    }
}