package com.example.foodrescueapp2;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodrescueapp2.data.DatabaseHelper;
import com.example.foodrescueapp2.model.User;

public class SignUpActivity extends AppCompatActivity {
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        EditText nameinput=findViewById(R.id.fullnametextview);
        EditText emailinput=findViewById(R.id.emailtextview);
        EditText phoneinput=findViewById(R.id.phonetextview);
        EditText addressinput=findViewById(R.id.addresstextview);
        EditText passwordinput=findViewById(R.id.passwordtextview);
        EditText confirmpasswordinput=findViewById(R.id.confirmpasswordtextview);
        Button saveButton = findViewById(R.id.savebutton);

        db = new DatabaseHelper(this);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=nameinput.getText().toString();
                String email=emailinput.getText().toString();
                String phone=phoneinput.getText().toString();
                String address=addressinput.getText().toString();
                String password=passwordinput.getText().toString();
                String confirmpassword = confirmpasswordinput.getText().toString();

                if(password.equals(confirmpassword))
                {
                    Log.i("Save Button check","Working");
                    long result=db.insertUser(new User(username,password,email,address,phone));
                    if (result > 0)
                    {
                        Toast.makeText(SignUpActivity.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(SignUpActivity.this, "Registration error!", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "Two passwords do not match!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}