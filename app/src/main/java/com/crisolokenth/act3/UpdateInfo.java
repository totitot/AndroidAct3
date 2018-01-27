package com.crisolokenth.act3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.SQLException;

public class UpdateInfo extends AppCompatActivity {
    static final String DATABASE_TABLE = "userdetailsTB";
    DBAdapter db;

    String name, username, address, password, email, gender;
    int genderIndex;
    Spinner mySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
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

        username = getIntent().getStringExtra("user_username");

        Cursor mCursor =
                db.db.query(true, DATABASE_TABLE, new String[] {"_id",
                                "name", "username", "password","address","gender","email"},
                        "username =?", new String[]{username},
                        null, null, null, null);

        if(mCursor.moveToFirst()) {
            name = mCursor.getString(1);
            password = mCursor.getString(3);
            address = mCursor.getString(4);
            gender = mCursor.getString(5);
            email = mCursor.getString(6);

            mySpinner = (Spinner)findViewById(R.id.spinner);
            genderIndex = getIndex(mySpinner, gender);


            ((EditText) findViewById(R.id.editText)).setText(name);
            ((EditText) findViewById(R.id.editText2)).setText(username);
            ((EditText) findViewById(R.id.editText5)).setText(address);
            ((EditText) findViewById(R.id.editText6)).setText(email);
            mySpinner.setSelection(genderIndex);
        }
    }

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    public void previousScreen(View view){
        Intent prev_screen = new Intent(this, HomeScreen.class);
        prev_screen.putExtras(getIntent());
        startActivity(prev_screen);
        finish();
    }

    public void updateUserDetails(View view){
        name = ((EditText)findViewById(R.id.editText)).getText().toString();
        username = ((EditText)findViewById(R.id.editText2)).getText().toString();
        address = ((EditText)findViewById(R.id.editText5)).getText().toString();
        email = ((EditText)findViewById(R.id.editText6)).getText().toString();
        gender = ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();

        ContentValues content = new ContentValues();
        content.put("name", name);
        content.put("username", username);
        content.put("password", password);
        content.put("address", address);
        content.put("gender", gender);
        content.put("email", email);
        db.db.update(DATABASE_TABLE, content, "username =?", new String[]{username});

        Intent next_screen = new Intent(this, HomeScreen.class);
        next_screen.putExtra("user_username", username);
        Toast.makeText(this,"Updated user details!", Toast.LENGTH_SHORT).show();
        startActivity(next_screen);
        finish();
    }

    public void updatePassword(View view){
        Intent next_screen = new Intent(this, UpdatePassword.class);
        next_screen.putExtra("user_username", username);
        startActivity(next_screen);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }
}
