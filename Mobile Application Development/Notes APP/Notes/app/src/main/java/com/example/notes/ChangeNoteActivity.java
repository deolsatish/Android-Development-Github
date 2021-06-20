package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.notes.data.DatabaseHelper;
import com.example.notes.model.Note;

import java.util.List;

public class ChangeNoteActivity extends AppCompatActivity {
    public int selectedposition;
    EditText inputtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_note);
        Intent intent3 = getIntent();
        selectedposition  = intent3.getIntExtra("selectedposition",0);
        inputtext=findViewById(R.id.updatetextview);
        DatabaseHelper db = new DatabaseHelper(this);
        List<Note> noteList=db.fetchAllNotes();
        inputtext.setText(noteList.get(selectedposition).getDescription());

    }

    public void updatefunction(View view) {
        inputtext=findViewById(R.id.updatetextview);
        DatabaseHelper db = new DatabaseHelper(this);
        List<Note> noteList=db.fetchAllNotes();
        db.updateNote(new Note(noteList.get(selectedposition).getUser_id(),inputtext.getText().toString()));
        Intent intent3 = getIntent();
        finishActivity(2);
        super.onBackPressed();

    }

    public void deletefunction(View view) {
        inputtext=findViewById(R.id.updatetextview);
        DatabaseHelper db = new DatabaseHelper(this);
        List<Note> noteList=db.fetchAllNotes();
        db.deleteNote(noteList.get(selectedposition));
        Intent intent3 = getIntent();
        finishActivity(2);
        super.onBackPressed();
    }
}