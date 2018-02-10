package com.totitot.act3;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLException;

public class RegisterUser extends AppCompatActivity {
    static final String DATABASE_TABLE = "userdetailsTB";
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        db = new DBAdapter(getApplicationContext());
        try{
            db.open();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

 //  @Override
 //  protected void onStart() {
 //      super.onStart();
 //      try{
 //          db.open();
 //      }catch(SQLException e){
 //          e.printStackTrace();
 //      }
 //  }

    public void previousScreen(View view){
        Intent prev_screen = new Intent(this, MainActivity.class);
        startActivity(prev_screen);
        finish();
    }

    public void saveDetails(View view){
       String name = ((EditText)findViewById(R.id.name)).getText().toString();
       String username = ((EditText)findViewById(R.id.username)).getText().toString();
       String password = ((EditText)findViewById(R.id.password)).getText().toString();
       String cpassword = ((EditText)findViewById(R.id.confirmpassword)).getText().toString();
       String address = ((EditText)findViewById(R.id.address)).getText().toString();
       String email = ((EditText)findViewById(R.id.email)).getText().toString();
       String gender = ((Spinner) findViewById(R.id.gender)).getSelectedItem().toString();


        if(password.equals(cpassword)) {
            ContentValues content = new ContentValues();
            content.put("name", name);
            content.put("username", username);
            content.put("password", password);
            content.put("address", address);
            content.put("gender", gender);
            content.put("email", email);
            db.db.insert(DATABASE_TABLE, null, content);

            Intent next_screen = new Intent(this, HomeScreen.class);
            next_screen.putExtra("user_username", username);
            startActivity(next_screen);
            Toast.makeText(this,"User registered!", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this,"Passwords do not match! Password: " + password + " confirm : " + " "+ cpassword,Toast.LENGTH_SHORT).show();

        }
        db.close();
    }

    public void clearContents(View view){
        ((EditText)findViewById(R.id.name)).getText().clear();
        ((EditText)findViewById(R.id.username)).getText().clear();
        ((EditText)findViewById(R.id.password)).getText().clear();
        ((EditText)findViewById(R.id.confirmpassword)).getText().clear();
        ((EditText)findViewById(R.id.address)).getText().clear();
        ((EditText)findViewById(R.id.email)).getText().clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
