package com.example.workouttimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView workhour;
    EditText workoutinput;
    ImageButton startbutton,pausebutton,stopbutton;
    Chronometer chronometer;
    public long pauseOffset;
    public boolean running;
    public boolean stop;
    String elapsedtimestring;
    long startsave;
    long time;
    String workouttype;
    public static final String MyPREFERENCES = "MyPrefs" ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        workhour=findViewById(R.id.workhour);
        workoutinput=findViewById(R.id.workouttypeinput);
        startbutton=findViewById(R.id.startbutton);
        pausebutton=findViewById(R.id.pausebutton);
        stopbutton=findViewById(R.id.stopbutton);

        chronometer=findViewById(R.id.chronometer);

        chronometer.setFormat("Time: %s");
        //Log.i("systemclockelapsetime",String.valueOf(SystemClock.elapsedRealtime()));
        chronometer.setBase(SystemClock.elapsedRealtime());
        running=false;
        stop=true;
        pauseOffset=0;
        elapsedtimestring="00:00";
        workouttype="Basic Workout";


        SharedPreferences sharedPref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String text=sharedPref.getString("text","");
        Log.i("sharedprefrences",text);

        workhour.setText(text);




        if(savedInstanceState!=null)
        {
            pauseOffset=savedInstanceState.getLong("pauseOffset");
            startsave=savedInstanceState.getLong("startsave");
            running=savedInstanceState.getBoolean("running");
            stop=savedInstanceState.getBoolean("stop");
            elapsedtimestring=savedInstanceState.getString("time");
            workouttype=savedInstanceState.getString("workouttype");

            workhour.setText("You spent "+elapsedtimestring+" on \""+workouttype+"\" last time");


            //Log.i("running value",String.valueOf(running));
            chronometer.setBase(startsave-pauseOffset);
            chronometer.start();

            if(running)
            {
                running=true;
                stop=false;
            }
            if(!running)
            {
                //Log.i("orientation stop","Working!!!!");
                //pausefunction(pausebutton);
                chronometer.stop();
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                running = false;
                stop=false;
                time =SystemClock.elapsedRealtime()-chronometer.getBase();
                showelapsedtime();
            }
            if(stop)
            {
                chronometer.setBase(SystemClock.elapsedRealtime());
                pauseOffset=0;
            }
        }

        


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if(!running)
        {
            startfunction(findViewById(R.id.startbutton));
            running=false;
        }
        outState.putLong("pauseOffset",pauseOffset);
        outState.putLong("startsave",startsave);
        outState.putBoolean("running",running);
        outState.putBoolean("stop",stop);
        outState.putString("time",elapsedtimestring);
        outState.putString("workouttype",workouttype);


    }

    public void showelapsedtime()
    {
        //time =SystemClock.elapsedRealtime()-chronometer.getBase();
        //Log.i("stoptime",String.valueOf(chronometer.getText()));
        //Log.i("stoptime2",String.valueOf(String.valueOf(time)));

    }

    public void startfunction(View view) {
        if (!running) {
            startsave=SystemClock.elapsedRealtime();
            chronometer.setBase(startsave- pauseOffset);
            //Log.i("systemclockelapsetime",String.valueOf(SystemClock.elapsedRealtime()));
            chronometer.start();
            running = true;
            stop=false;

        }


    }

    public void pausefunction(View view) {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
            stop=false;
            time =SystemClock.elapsedRealtime()-chronometer.getBase();
            showelapsedtime();
        }
        //Log.i("pausefunctionw","working!!!!!!!!!!!");
        //Log.i("running value",String.valueOf(running));
    }

    public void stopfunction(View view) {
        if(running)
        {
            pausebutton=findViewById(R.id.pausebutton);
            pausefunction(pausebutton);
        }
        elapsedtimestring=String.valueOf(chronometer.getText());

        showelapsedtime();
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        stop=true;
        running=false;


        workhour=findViewById(R.id.workhour);
        workoutinput=findViewById(R.id.workouttypeinput);

        workouttype= workoutinput.getText().toString();
        workhour.setText("You spent "+elapsedtimestring+" on \""+workouttype+"\" last time");

        SharedPreferences sharedPref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("text","You spent "+elapsedtimestring+" on \""+workouttype+"\" last time");
        editor.apply();


    }
}