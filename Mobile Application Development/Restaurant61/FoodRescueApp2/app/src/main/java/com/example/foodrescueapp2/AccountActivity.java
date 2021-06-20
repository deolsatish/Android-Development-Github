package com.example.foodrescueapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.foodrescueapp2.data.DatabaseHelper;
import com.example.foodrescueapp2.model.Food;
import com.example.foodrescueapp2.model.User;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity {
    DatabaseHelper db;
    List<com.example.foodrescueapp2.model.User> userList= new ArrayList<>();
    User user;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        EditText nameinput=findViewById(R.id.fullnametextview);
        EditText emailinput=findViewById(R.id.emailtextview);
        EditText phoneinput=findViewById(R.id.phonetextview);
        EditText addressinput=findViewById(R.id.addresstextview);
        EditText passwordinput=findViewById(R.id.passwordtextview);
        EditText confirmpasswordinput=findViewById(R.id.confirmpasswordtextview);

        db = new DatabaseHelper(this);

        Intent intent=getIntent();
        username=intent.getStringExtra("username");

        userList=db.fetchAllUsers();

        Log.i("username",username);

        for(int i=0;i<userList.size();i++)
        {
            Log.i(String.valueOf(i),userList.get(i).getUsername());
            if(String.valueOf(userList.get(i).getUsername()).equals(username))
            {
                Log.i("Comparison","Working");
                user=userList.get(i);

            }
        }

        Log.i("RecyclerView number",String.valueOf(userList.size()));
        Log.i("user.getEmail()",String.valueOf(user.getEmail()));

        nameinput.setText(user.getUsername());
        emailinput.setText(user.getEmail());
        phoneinput.setText(user.getPhone());
        addressinput.setText(user.getAddress());
        passwordinput.setText("**********");
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
            Intent intent = new Intent(AccountActivity.this, homeActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id == R.id.action_Account) {
            return true;
        }
        else if (id == R.id.action_Mylist) {
            Intent intent = new Intent(AccountActivity.this, mylistActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}