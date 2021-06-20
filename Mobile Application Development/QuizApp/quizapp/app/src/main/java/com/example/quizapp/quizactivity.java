package com.example.quizapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class quizactivity extends AppCompatActivity {

    //Red color - FA0D0D
    //Green color - 8BC34A
    public int answered=0;
    public int questionanswered=0;
    public int selectedanswer=0;
    public int score=0;
    String[][] choice={
            {"Question 1: In What language is Android libraries written","c/c++ ","Java","python","1"},
            {"Question 2: What do Toast do?","display text","provide feedback in a popup","display image","2"},
            {"Question 3: An application starts with what?","SecondActivity","MainActivity","Main()","2"},
            {"Question 4: How many Lifecycle states can an activity have?","3","5","4","3"},
            {"Question 5: Number of core set of callbacks an activity class provides?","6","7","8","1"}
    };
    String Name="Default Name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizactivity);
        TextView welcometext=findViewById(R.id.welcometextview);
        TextView progressindicator=findViewById(R.id.progressindicator);
        ProgressBar progressbar=findViewById(R.id.progressBar3);
        TextView questiontitleview=findViewById(R.id.questiontitleview);
        TextView questionview=findViewById(R.id.questionview);
        Button answer1button=findViewById(R.id.answer1);
        Button answer2button=findViewById(R.id.answer2);
        Button answer3button=findViewById(R.id.answer3);
        Button submitbutton=findViewById(R.id.submit);


        questionview.setText(choice[0][0].toString());
        answer1button.setText(choice[0][1]);
        answer2button.setText(choice[0][2]);
        answer3button.setText(choice[0][3]);
        submitbutton.setText("Submit");

        Intent intent3 = getIntent();
        Name = intent3.getStringExtra("username");
        welcometext.setText("Welcome "+Name+"!!");


    }

    public void answer1function(View view) {
        if(selectedanswer==1)
        {
            Context context = getApplicationContext();
            CharSequence text = String.valueOf(questionanswered);
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else
        {
            Button answer1button=findViewById(R.id.answer1);

            selectedanswer=1;
            answer1button.setBackgroundColor(Color.DKGRAY);




        }
    }

    public void answer2function(View view) {
        if(selectedanswer==2)
        {
            //Error Messsage
        }
        else
        {
            Button answer2button=findViewById(R.id.answer2);

            selectedanswer=2;
            answer2button.setBackgroundColor(Color.DKGRAY);

        }
    }

    public void answer3funcion(View view) {
        if(selectedanswer==3)
        {
            //Error Messsage
        }
        else
        {
            Button answer3button=findViewById(R.id.answer3);
            selectedanswer=3;
            answer3button.setBackgroundColor(Color.DKGRAY);

        }
    }

    public void submitfunction(View view) {
        if(answered==0)
        {
            if(selectedanswer==0)
            {
                //Error Message

            }
            else
            {



                Button answer1button=findViewById(R.id.answer1);
                Button answer2button=findViewById(R.id.answer2);
                Button answer3button=findViewById(R.id.answer3);


                switch(selectedanswer)
                {
                    case 0:
                        break;

                    case 1:
                    {
                        if(selectedanswer!=Integer.parseInt(choice[questionanswered][4]))
                        {
                            answer1button.setBackgroundColor(Color.RED);
                        }
                        else
                        {

                        }

                        break;
                    }
                    case 2:
                    {
                        if(selectedanswer!=Integer.parseInt(choice[questionanswered][4]))
                        {
                            answer2button.setBackgroundColor(Color.RED);
                        }
                        else
                        {

                        }
                        break;
                    }
                    case 3:
                    {
                        if(selectedanswer!=Integer.parseInt(choice[questionanswered][4]))
                        {
                            answer3button.setBackgroundColor(Color.RED);
                        }
                        else
                        {

                        }
                        break;
                    }
                }
                switch(Integer.parseInt(choice[questionanswered][4]))
                {
                    case 0:
                        break;

                    case 1:
                    {
                        answer1button.setBackgroundColor(Color.GREEN);

                        break;
                    }
                    case 2:
                    {
                        answer2button.setBackgroundColor(Color.GREEN);
                        break;
                    }
                    case 3:
                    {
                        answer3button.setBackgroundColor(Color.GREEN);
                        break;
                    }
                }
                Button submitbutton=findViewById(R.id.submit);
                submitbutton.setBackgroundColor(Color.LTGRAY);
                submitbutton.setText("Next");
                answered=1;


            }

        }
        else
        {
            int answer=Integer.parseInt(choice[questionanswered][4]);
            questionanswered++;
            if(selectedanswer==answer)
            {
                score++;
            }


            if(questionanswered==5)
            {

                Context context = getApplicationContext();
                CharSequence text = String.valueOf(score);
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Intent intent2=new Intent(getApplicationContext(), finishactivity.class);
                intent2.putExtra("name", Name);





                intent2.putExtra("score", String.valueOf(score));
                intent2.putExtra("score", String.valueOf(score));
                startActivityForResult(intent2, 1);

            }
            else
            {
                Button answer1button=findViewById(R.id.answer1);
                Button answer2button=findViewById(R.id.answer2);
                Button answer3button=findViewById(R.id.answer3);
                answer1button.setBackgroundColor(Color.WHITE);
                answer2button.setBackgroundColor(Color.WHITE);
                answer3button.setBackgroundColor(Color.WHITE);


                TextView progressindicator=findViewById(R.id.progressindicator);
                ProgressBar progressbar=findViewById(R.id.progressBar3);
                TextView questionview=findViewById(R.id.questionview);

                progressbar.setProgress(questionanswered+1);
                String number= String.valueOf(questionanswered+1);
                progressindicator.setText(number+"/5");
                answered=0;
                selectedanswer=0;

                questionview.setText(choice[questionanswered][0]);
                answer1button.setText(choice[questionanswered][1]);
                answer2button.setText(choice[questionanswered][2]);
                answer3button.setText(choice[questionanswered][3]);
                Button submitbutton=findViewById(R.id.submit);
                submitbutton.setText("Submit");


            }





        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();

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