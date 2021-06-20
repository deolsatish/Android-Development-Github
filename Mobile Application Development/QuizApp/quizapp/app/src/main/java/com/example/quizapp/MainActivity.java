package com.example.quizapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText name = findViewById(R.id.name);
        Intent intent4 = getIntent();
        String Name = intent4.getStringExtra("username");
        name.setText(Name);


    }

    public void startfunction(View view) {


        EditText name = findViewById(R.id.name);
        Intent intent=new Intent(getApplicationContext(), quizactivity.class);
        intent.putExtra("username", name.getText().toString());
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        finish();
        super.onActivityResult(requestCode, resultCode, data);
        EditText name = findViewById(R.id.name);
        if (resultCode == RESULT_OK)
        {
            if (requestCode ==1)
            {
                finish();
            }
        }
        else if (resultCode ==RESULT_CANCELED)
        {

        }



    }
}


























