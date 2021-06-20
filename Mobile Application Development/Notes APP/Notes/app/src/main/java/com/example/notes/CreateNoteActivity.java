package com.example.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.notes.data.DatabaseHelper;
import com.example.notes.model.Note;

import java.util.ArrayList;
import java.util.List;

public class CreateNoteActivity extends AppCompatActivity {
    EditText inputtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        inputtext=findViewById(R.id.createtextview);
        inputtext.setText("Enter your not here");
    }

    public void save(View view) {

        inputtext=findViewById(R.id.createtextview);
        DatabaseHelper db = new DatabaseHelper(this);
        Note note=new Note();
        note.setDescription(inputtext.getText().toString());
        Log.i("create note",inputtext.getText().toString());
        db.insertNote(note);
        Intent intent3 = getIntent();
        finish();
    }
}



