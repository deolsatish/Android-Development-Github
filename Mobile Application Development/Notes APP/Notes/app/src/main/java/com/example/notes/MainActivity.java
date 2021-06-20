package com.example.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.notes.data.DatabaseHelper;
import com.example.notes.model.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void createnewnotefunction(View view) {
        Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
        startActivityForResult(intent, 1);

    }

    public void showallnotesfunction(View view) {
        Intent intent = new Intent(MainActivity.this, ShowNotesActivity.class);
        startActivityForResult(intent, 1);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK)
        {
            if (requestCode ==2)
            {
                Intent intent = new Intent(MainActivity.this, ShowNotesActivity.class);
                startActivityForResult(intent, 1);
            }
        }
        else if (resultCode ==RESULT_CANCELED)
        {


        }



    }
}