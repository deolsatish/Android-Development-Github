package com.example.restaurantmapapp2.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.restaurantmapapp2.model.Place;
import com.example.restaurantmapapp2.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" + Util.PLACE_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Util.PLACE_NAME + " TEXT," + Util.PLACE_LAT + " TEXT," + Util.PLACE_LONG + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String DROP_USER_TABLE = "DROP TABLE IF EXISTS";
        sqLiteDatabase.execSQL(DROP_USER_TABLE, new String[]{Util.TABLE_NAME});
        onCreate(sqLiteDatabase);
    }

    /*public boolean deleteCart (Cart cart)
    {
        Log.i("insert CART check","Working or Not");
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result=db.delete(Util.TABLE_NAME3, Util.CART_ID + "=" + cart.getCart_id(), null) > 0;
        db.close();
        return result;
    }
    public int deleteAllCart (Cart cart)
    {
        Log.i("insert CART check","Working or Not");
        SQLiteDatabase db = this.getWritableDatabase();
        int result=db.delete(Util.DATABASE_NAME,null,null);
        db.close();
        return result;
    }*/

    public int deleteAllPlace (Place place) {
        Log.i("insert CART check", "Working or Not");
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(Util.TABLE_NAME, null, null);
        db.close();
        return result;
    }
    public long insertPlace (Place place)
    {
        Log.i("insert PLace check","Working or Not");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.PLACE_NAME, place.getPlace_name());
        contentValues.put(Util.PLACE_LAT, place.getPlace_lat());
        contentValues.put(Util.PLACE_LONG, place.getPlace_long());
        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        Log.i("insert place",String.valueOf(newRowId));
        return newRowId;
    }


    public List<Place> fetchAllPlaces (){
        List<Place> placeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Place place = new Place();
                place.setPlace_id(cursor.getInt(0));
                place.setPlace_name(cursor.getString(1));
                place.setPlace_lat(cursor.getString(2));
                place.setPlace_long(cursor.getString(3));

                placeList.add(place);

            } while (cursor.moveToNext());

        }

        return placeList;
    }


}
