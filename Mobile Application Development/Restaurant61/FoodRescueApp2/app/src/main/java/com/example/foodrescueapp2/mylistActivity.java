package com.example.foodrescueapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.foodrescueapp2.data.DatabaseHelper;
import com.example.foodrescueapp2.model.Food;

import java.util.ArrayList;
import java.util.List;

public class mylistActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    List<Food> foodList= new ArrayList<>();
    List<Food> myfoodList= new ArrayList<>();
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Your Shared Food List");
        setContentView(R.layout.activity_mylist);
        DatabaseHelper db = new DatabaseHelper(mylistActivity.this);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");

        foodList=db.fetchAllFoods();

        Log.i("username",username);

        for(int i=0;i<foodList.size();i++)
        {
            Log.i(String.valueOf(i),foodList.get(i).getUser_name());
            if(String.valueOf(foodList.get(i).getUser_name()).equals(username))
            {
                Log.i("Comparison","Working");
                myfoodList.add(foodList.get(i));
            }
        }

        Log.i("RecyclerView number",String.valueOf(myfoodList.size()));

        recyclerView=findViewById(R.id.recyclerView);
        recyclerViewAdapter=new RecyclerViewAdapter(myfoodList,this,this::onItemClick);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));





    }

    private void onItemClick(int position) {
        switch (position){
            case 0:
                Toast.makeText(this, "You clicked on Station St.", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "You clicked on South Bank ", Toast.LENGTH_SHORT).show();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            Intent intent = new Intent(mylistActivity.this, homeActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id == R.id.action_Account) {
            Intent intent = new Intent(mylistActivity.this, AccountActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id == R.id.action_Mylist) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addfoodbutton(View view) {
        Intent intent = new Intent(mylistActivity.this, addfoodActivity.class);
        intent.putExtra("username",username);
        startActivity(intent);
        finish();
    }
    public void share()
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}