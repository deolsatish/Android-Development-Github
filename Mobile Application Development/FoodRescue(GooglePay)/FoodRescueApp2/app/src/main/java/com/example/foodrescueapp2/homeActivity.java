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

public class homeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    List<Food> foodList= new ArrayList<>();
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Discover Free Food");
        setContentView(R.layout.activity_home);
        DatabaseHelper db = new DatabaseHelper(homeActivity.this);

        foodList=db.fetchAllFoods();
        Log.i("RecyclerView number",String.valueOf(foodList.size()));

        recyclerView=findViewById(R.id.recyclerView);
        recyclerViewAdapter=new RecyclerViewAdapter(foodList,this,this::onItemClick);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent=getIntent();
        username=intent.getStringExtra("username");




    }

    private void onItemClick(int position) {
        Intent intent = new Intent(homeActivity.this, fooditemActivity.class);
        intent.putExtra("foodid",foodList.get(position).getFood_id());
        startActivity(intent);
        finish();

        /*switch (position){
            case 0:
                Toast.makeText(this, "You clicked on Station St.", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "You clicked on South Bank ", Toast.LENGTH_SHORT).show();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }*/
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
            return true;
        }
        else if (id == R.id.action_Account) {
            Intent intent = new Intent(homeActivity.this, AccountActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id == R.id.action_Mylist) {
            Intent intent = new Intent(homeActivity.this, mylistActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id == R.id.action_Cart) {
            Intent intent = new Intent(homeActivity.this, cartActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addfoodbutton(View view) {
        Intent intent = new Intent(homeActivity.this, addfoodActivity.class);
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