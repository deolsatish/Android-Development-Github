package com.example.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.notes.data.DatabaseHelper;
import com.example.notes.model.Note;

import java.util.ArrayList;
import java.util.List;

public class ShowNotesActivity extends AppCompatActivity {
    ListView notelistview;
    ArrayList<String> notearraylist;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notes);
        Log.i("ShowNotes","working!!");
        notelistview=findViewById(R.id.notelistview);
        notearraylist=new ArrayList<>();
        DatabaseHelper db=new DatabaseHelper(ShowNotesActivity.this);
        List<Note> noteList=db.fetchAllNotes();

        for(Note note:noteList)
        {
            notearraylist.add(note.getDescription());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notearraylist);
        notelistview.setAdapter(adapter);
        notelistview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(), ChangeNoteActivity.class);
                intent.putExtra("selectedposition", position);
                startActivityForResult(intent, 2);

            }


        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();

        if (resultCode == RESULT_OK)
        {
            if (requestCode ==2)
            {

                finishActivity(2);

            }
            else
            {
                finish();
            }
        }
        else if (resultCode ==RESULT_CANCELED)
        {


        }



    }
}