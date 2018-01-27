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

public class UpdatePassword extends AppCompatActivity {
    static final String DATABASE_TABLE = "userdetailsTB";
    DBAdapter db;

    String username, password, oldpassword, newpassword, cnewpassword;
    String email, address, gender, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
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
        }
    }

    public void previousScreen(View view){
        Intent prev_screen = new Intent(this, UpdateInfo.class);
        prev_screen.putExtras(getIntent());
        startActivity(prev_screen);
        finish();
    }

    public void updatePassword(View view){
        oldpassword = ((EditText)findViewById(R.id.editText)).getText().toString();
        newpassword = ((EditText)findViewById(R.id.editText2)).getText().toString();
        cnewpassword = ((EditText)findViewById(R.id.editText3)).getText().toString();

        if(password.equals(oldpassword)){
            if(newpassword.equals(cnewpassword)){

                ContentValues content = new ContentValues();
                content.put("name", name);
                content.put("username", username);
                content.put("password", password);
                content.put("address", address);
                content.put("gender", gender);
                content.put("email", email);
                db.db.update(DATABASE_TABLE, content, "username =?", new String[]{username});

                Intent next_screen = new Intent(this, UpdateInfo.class);
                next_screen.putExtra("user_username", username);

                Toast.makeText(this,"Updated user password!", Toast.LENGTH_SHORT).show();
                startActivity(next_screen);
                finish();
            }
            else{
                Toast.makeText(this,"New password does not match confirmation!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this,"Incorrect password!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }
}
