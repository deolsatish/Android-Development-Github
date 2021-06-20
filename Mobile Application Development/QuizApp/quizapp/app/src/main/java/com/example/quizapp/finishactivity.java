package com.example.quizapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class finishactivity extends AppCompatActivity {
    String score;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finishactivity);
        TextView congratstext=findViewById(R.id.congratulationtext);
        TextView scoretext=findViewById(R.id.scoretext);
        Button newquizbutton=findViewById(R.id.newquizbutton);
        Button finishbutton=findViewById(R.id.finishbutton);
        Intent intent3 = getIntent();
        name = intent3.getStringExtra("name");
        score = intent3.getStringExtra("score");
        congratstext.setText("Welcome "+name+"!!");
        scoretext.setText("YOUR SCORE: "+score+"/5");
    }

    public void finishfunction(View view) {

        finish();
        System.exit(0);
    }

    public void newquizfunction(View view) {
        Intent intent2=new Intent(getApplicationContext(), MainActivity.class);
        intent2.putExtra("username", name);
        startActivityForResult(intent2, 1);
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