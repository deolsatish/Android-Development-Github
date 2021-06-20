package com.example.foodrescueapp2.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.foodrescueapp2.model.Cart;
import com.example.foodrescueapp2.model.Food;
import com.example.foodrescueapp2.model.User;
import com.example.foodrescueapp2.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" + Util.USER_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Util.USERNAME + " TEXT," + Util.EMAIL + " TEXT," + Util.PHONE + " TEXT,"+ Util.ADDRESS + " TEXT,"+ Util.PASSWORD + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);

        String CREATE_FOOD_TABLE = "CREATE TABLE " + Util.TABLE_NAME2 + "(" + Util.FOOD_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT , " + Util.USERNAME +" TEXT,"
                + Util.TITLE + " TEXT," + Util.DESCRIPTION + " TEXT," + Util.DATE + " TEXT,"+ Util.PICKUPTIME + " TEXT,"+
                Util.QUANTITY + " TEXT," + Util.LOCATION + " TEXT," + Util.PRICE +" TEXT,"  + Util.IMAGE + " BLOB)";
        sqLiteDatabase.execSQL(CREATE_FOOD_TABLE);

        String CREATE_CART_TABLE = "CREATE TABLE " + Util.TABLE_NAME3 + "(" + Util.CART_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT , "+ Util.FOOD_ID + " INTEGER)";
        sqLiteDatabase.execSQL(CREATE_CART_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String DROP_USER_TABLE = "DROP TABLE IF EXISTS";
        sqLiteDatabase.execSQL(DROP_USER_TABLE, new String[]{Util.TABLE_NAME});
        onCreate(sqLiteDatabase);
    }
    public long insertCart (Cart cart)
    {
        Log.i("insert CART check","Working or Not");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.FOOD_ID, cart.getFood_id());
        long newRowId = db.insert(Util.TABLE_NAME3, null, contentValues);
        db.close();
        Log.i("insert cart",String.valueOf(newRowId));
        return newRowId;
    }
    public boolean deleteCart (Cart cart)
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
        int result=db.delete(Util.TABLE_NAME3,null,null);
        db.close();
        return result;
    }
    public long insertUser (User user)
    {
        Log.i("insert User check","Working or Not");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.USERNAME, user.getUsername());
        contentValues.put(Util.EMAIL, user.getEmail());
        contentValues.put(Util.PHONE, user.getPhone());
        contentValues.put(Util.ADDRESS, user.getAddress());
        contentValues.put(Util.PASSWORD, user.getPassword());
        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        Log.i("insert note",String.valueOf(newRowId));
        return newRowId;
    }
    public long insertFood (Food food)
    {
        Log.i("insert User check","Working or Not");
        Log.i("insert User check",String.valueOf(food.getUser_name()));
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.USERNAME, food.getUser_name());
        contentValues.put(Util.TITLE, food.getTitle());
        contentValues.put(Util.DESCRIPTION, food.getDescription());
        contentValues.put(Util.DATE, food.getDate());
        contentValues.put(Util.PICKUPTIME, food.getPickuptime());
        contentValues.put(Util.QUANTITY, food.getQuantity());
        contentValues.put(Util.LOCATION, food.getLocation());
        contentValues.put(Util.PRICE, food.getPrice());
        contentValues.put(Util.IMAGE, food.getImage());
        long newRowId = db.insert(Util.TABLE_NAME2, null, contentValues);
        db.close();
        Log.i("insert note",String.valueOf(newRowId));
        return newRowId;
    }
    public boolean fetchUser(String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.USER_ID}, Util.USERNAME + "=? and " + Util.PASSWORD + "=?",
                new String[] {username, password}, null, null, null);
        int numberOfRows = cursor.getCount();
        db.close();

        if (numberOfRows > 0)
            return  true;
        else
            return false;
    }

    public List<User> fetchAllUsers (){
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUser_id(cursor.getInt(0));
                user.setUsername(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setPhone(cursor.getString(3));
                user.setAddress(cursor.getString(4));
                user.setPassword(cursor.getString(5));

                userList.add(user);

            } while (cursor.moveToNext());

        }

        return userList;
    }
    public List<Food> fetchAllFoods (){
        List<Food> foodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.TABLE_NAME2;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setFood_id(cursor.getInt(0));
                food.setUser_name(cursor.getString(1));
                food.setTitle(cursor.getString(2));
                food.setDescription(cursor.getString(3));
                food.setDate(cursor.getString(4));
                food.setPickuptime(cursor.getString(5));
                food.setQuantity(cursor.getString(6));
                food.setLocation(cursor.getString(7));
                food.setPrice(cursor.getString(8));
                food.setImage(cursor.getBlob(9));

                foodList.add(food);

            } while (cursor.moveToNext());

        }

        return foodList;
    }
    public List<Cart> fetchAllCart (){
        List<Cart> cartList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.TABLE_NAME3;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                cart.setCart_id(cursor.getInt(0));
                cart.setFood_id(cursor.getInt(1));
                cartList.add(cart);

            } while (cursor.moveToNext());

        }

        return cartList;
    }


}
