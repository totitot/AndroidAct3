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

    }

    @Override
    protected void onStart() {
        super.onStart();
        try{
            db.open();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void previousScreen(View view){
        Intent prev_screen = new Intent(this, MainActivity.class);
        startActivity(prev_screen);
        finish();
    }

    public void saveDetails(View view){
       String name = ((EditText)findViewById(R.id.editText)).getText().toString();
       String username = ((EditText)findViewById(R.id.editText2)).getText().toString();
       String password = ((EditText)findViewById(R.id.editText3)).getText().toString();
       String cpassword = ((EditText)findViewById(R.id.editText4)).getText().toString();
       String address = ((EditText)findViewById(R.id.editText5)).getText().toString();
       String email = ((EditText)findViewById(R.id.editText6)).getText().toString();
       String gender = ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();


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
            Toast.makeText(this,"Passwords do not match! Password: " + password + "confirm : " + " "+ cpassword,Toast.LENGTH_SHORT).show();

        }
        db.close();
    }

    public void clearContents(View view){
        ((EditText)findViewById(R.id.editText)).getText().clear();
        ((EditText)findViewById(R.id.editText2)).getText().clear();
        ((EditText)findViewById(R.id.editText3)).getText().clear();
        ((EditText)findViewById(R.id.editText4)).getText().clear();
        ((EditText)findViewById(R.id.editText5)).getText().clear();
        ((EditText)findViewById(R.id.editText6)).getText().clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }
}
