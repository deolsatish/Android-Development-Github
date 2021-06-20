package com.example.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public Spinner spinner;
    //public static final String[] options = {"Metre", "Celsius", "Kilogram"};
    EditText numberinput;
    TextView unitview1, unitview2, unitview3;
    TextView unittextview1, unittextview2, unittextview3;
    ImageButton lengthbutton, thermometerbutton, weightbutton;
    int globalposition;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner)findViewById(R.id.spinner);
        //ArrayAdapter<String>adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,options);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.input_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        numberinput=findViewById(R.id.numberinput);
        unitview1=findViewById(R.id.unitview1);
        unitview2=findViewById(R.id.unitview2);
        unitview3=findViewById(R.id.unitview3);
        unittextview1=findViewById(R.id.unittextview1);
        unittextview2=findViewById(R.id.unittextview2);
        unittextview3=findViewById(R.id.unittextview3);



        //Length Button FUNCTION
        lengthbutton=findViewById((R.id.lengthbutton));
        lengthbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalposition==0)
                {
                    unittextview1.setText("Centimetre");
                    unittextview2.setText("Foot");
                    unittextview3.setText("Inch");
                    //FOR 1st Centimtre UNIT
                    Double n1=Double.parseDouble(numberinput.getText().toString());
                    String str1=String.format("%.2f", n1*100);
                    unitview1.setText(str1);

                    //For 2nd Foot unit
                    Double n2=Double.parseDouble(numberinput.getText().toString());
                    String str2=String.format("%.2f", n2*3.28084);
                    unitview2.setText(str2);


                    //For 3d inch unit
                    Double n3=Double.parseDouble(numberinput.getText().toString());
                    String str3=String.format("%.2f", n3*39.3701);
                    unitview3.setText(str3);
                }
                else
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Please select the correct conversion icon!";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });

        //Thermometer function
        thermometerbutton=findViewById((R.id.thermometerbutton));
        thermometerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalposition==1)
                {
                    unittextview1.setText("Fahrenheit");
                    unittextview2.setText("Kelvin");
                    unittextview3.setText("");
                    //FOR 1st Fahrenheit UNIT
                    Double n1=Double.parseDouble(numberinput.getText().toString());
                    String str1=String.format("%.2f", n1*33.8);
                    unitview1.setText(str1);

                    //For 2nd Kelvin unit
                    Double n2=Double.parseDouble(numberinput.getText().toString());
                    String str2=String.format("%.2f", n2+274.15);
                    unitview2.setText(str2);


                    //3rd is null
                    unitview3.setText("");
                }
                else
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Please select the correct conversion icon!";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });


        //Weight Button FUNCTION
        weightbutton=findViewById((R.id.weightbutton));
        weightbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalposition==2)
                {
                    unittextview1.setText("Gram");
                    unittextview2.setText("Ounce(Oz)");
                    unittextview3.setText("Pound(lb)");
                    //FOR 1st Gram UNIT
                    Double n1=Double.parseDouble(numberinput.getText().toString());
                    String str1=String.format("%.2f", n1*1000);
                    unitview1.setText(str1);

                    //For 2nd Ounce unit
                    Double n2=Double.parseDouble(numberinput.getText().toString());
                    String str2=String.format("%.2f", n2*35.274);
                    unitview2.setText(str2);


                    //For 3d Pound unit
                    Double n3=Double.parseDouble(numberinput.getText().toString());
                    String str3=String.format("%.2f", n3*2.20462);
                    unitview3.setText(str3);
                }
                else
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Please select the correct conversion icon!";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        globalposition=position;

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected


                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

}