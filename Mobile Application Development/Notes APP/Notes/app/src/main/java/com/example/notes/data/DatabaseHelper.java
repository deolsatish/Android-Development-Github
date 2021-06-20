package com.example.notes.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.notes.model.Note;
import com.example.notes.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_note_table="CREATE TABLE "+Util.TABLE_NAME+
                "("+Util.NOTE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                Util.NOTES_DESC+" TEXT)";
        db.execSQL(create_note_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String DROP_USER_TABLE = "DROP TABLE IF EXISTS";
        sqLiteDatabase.execSQL(DROP_USER_TABLE, new String[]{Util.TABLE_NAME});

        onCreate(sqLiteDatabase);

    }


    public long insertNote(Note note)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Util.NOTES_DESC,note.getDescription());
        long newRowId=db.insert(Util.TABLE_NAME,null,contentValues);
        db.close();
        Log.i("insert note",String.valueOf(newRowId));
        return newRowId;
    }
    public List<Note> fetchAllNotes()
    {
        List<Note> noteList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.TABLE_NAME ;
        Cursor cursor=db.rawQuery(selectAll,null);
        Log.i("fetch note",String.valueOf(selectAll));

        if(cursor.moveToFirst())
        {
            do {
                Log.i("fetch note",String.valueOf(cursor.getInt(0)));
                Note note=new Note();
                note.setUser_id(cursor.getInt(0));
                note.setDescription(cursor.getString(1));
                noteList.add(note);
            }while(cursor.moveToNext());
        }
        return noteList;

    }
    public int updateNote(Note note)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Util.NOTES_DESC,note.getDescription());
        Log.i("UPDATE note",String.valueOf(note.getUser_id()));
        return db.update(Util.TABLE_NAME,contentValues,Util.NOTE_ID + "=?", new String[]{String.valueOf(note.getUser_id())});
    }
    public int deleteNote(Note note)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        return db.delete(Util.TABLE_NAME,Util.NOTE_ID + "=?", new String[]{String.valueOf(note.getUser_id())});
    }
}
